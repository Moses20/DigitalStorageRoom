package com.example.digitalstorageroom.scanner

import android.content.Context
import android.util.Log
import android.util.Size
import androidx.camera.compose.CameraXViewfinder
import androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceRequest
import androidx.camera.core.UseCase
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.camera.view.CameraController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    modifier: Modifier = Modifier,
    cameraViewModel: CameraViewModel,
) {
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    if (cameraPermissionState.status.isGranted) {
        CameraContent(
            modifier,
            cameraViewModel
        )
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .wrapContentSize()
                .widthIn(max = 480.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val textToShow = if (cameraPermissionState.status.shouldShowRationale) {
                "Whoops! Looks like we need your camera to work our magic!" +
                        "Don't worry, we just wanna see your pretty face (and maybe some cats).  " +
                        "Grant us permission and let's get this party started!"
            } else {
                "Hi there! We need your camera to work our magic! ✨\n" +
                        "Grant us permission and let's get this party started! \uD83C\uDF89"
            }
            Text(textToShow, textAlign = TextAlign.Center)
            Spacer(Modifier.height(16.dp))
            Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                Text("Unleash the Camera!")
            }
        }
    }
}

//TODO Patrick: use Hilt instead: https://developer.android.com/training/dependency-injection
@Composable
fun CameraContent(
    modifier: Modifier = Modifier,
    cameraViewModel: CameraViewModel,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val context = LocalContext.current
    // Fix: Use remember to prevent creating a new ViewModel and UseCase on every recomposition
    //val cameraViewModel = remember { CameraViewModel() }
    val surfaceRequest by cameraViewModel.surfaceRequest.collectAsStateWithLifecycle()

    LaunchedEffect(cameraViewModel, lifecycleOwner) {
        cameraViewModel.bindToCamera(context.applicationContext, lifecycleOwner)
    }

    Box(modifier = modifier.fillMaxSize()) {
        surfaceRequest?.let { request ->
            CameraXViewfinder(
                surfaceRequest = request,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

class BarcodeScanner {

    private val options = BarcodeScannerOptions
        .Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_EAN_8,
            Barcode.FORMAT_EAN_13
        )
        .build()

    private val scanner = BarcodeScanning.getClient(options)

    //TODO: Only scan if barcode is visible

    @androidx.annotation.OptIn(ExperimentalGetImage::class)
    suspend fun scan(imageProxy: ImageProxy) {
        Log.i("INFO", "Analyzing image")
        val image = imageProxy.image ?: return
        val rotationDegrees = imageProxy.imageInfo.rotationDegrees

        imageProxy.use {
            scanner.process(InputImage.fromMediaImage(image, rotationDegrees))
                .addOnSuccessListener { barcodes ->
                    barcodes
                        .also {
                            Log.i("INFO", "Barcodes found: ${it.map({ barcode -> barcode.rawValue }).joinToString(", ")}")
                        }
                        // The barcode with the biggest bounding box is deemed to be the one focused by the user
                        .maxWith { barcode1, barcode2 ->
                        barcode1.boundingBox!!.width() * barcode1.boundingBox!!.height() -
                                barcode2.boundingBox!!.width() * barcode2.boundingBox!!.height()
                    }.let { barcode ->
                        Log.i("INFO", "Barcode found: ${barcode.rawValue}")
                    }
                }
        }

    }
}

class CodeAnalyzer : ImageAnalysis.Analyzer {

    val barcodeScanner = BarcodeScanner()

    @androidx.annotation.OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        /*Log.i("INFO", "Analyzing image")
        CoroutineScope(Dispatchers.Default).launch {
            barcodeScanner.scan(imageProxy)
        }
        imageProxy.close()*/
    }
}

class CameraViewModel : ViewModel() {
    private val _surfaceRequest = MutableStateFlow<SurfaceRequest?>(null)
    val surfaceRequest: StateFlow<SurfaceRequest?> = _surfaceRequest

    private val cameraPreview = Preview
        .Builder()
        .build()
        .apply {
            setSurfaceProvider {
                _surfaceRequest.value = it
            }
        }

    //TODO Patrick: cleanup this class. CameraViewModel should be decoupled from analyzer
    // so that the we can provide different analyzers
    private val analysisExecutor = Executors.newSingleThreadExecutor()

    private val analyzer = ImageAnalysis.Builder()
        .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
        .build()
        .also { it.setAnalyzer(analysisExecutor, CodeAnalyzer()) }

    private val scanner = BarcodeScanner()
    val imageCapture = ImageCapture
        .Builder()
        .build()

    suspend fun bindToCamera(
        appContext: Context,
        lifecycleOwner: LifecycleOwner,
        vararg useCases: UseCase
    ) {
        val processCameraProvider = ProcessCameraProvider.awaitInstance(appContext)


        // unbindAll before binding to ensure a clean state and avoid TimeoutException
        processCameraProvider.unbindAll()

        try {
            processCameraProvider.bindToLifecycle(
                lifecycleOwner,
                DEFAULT_BACK_CAMERA,
                cameraPreview,
                imageCapture,
                //analyzer,
                *useCases
            )
            awaitCancellation()
        } finally {
            processCameraProvider.unbindAll()
        }
    }

    suspend fun takePhoto() {
        println("INFO: I have taken a photo!!")
        imageCapture.takePicture(
            analysisExecutor,
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    Log.i("INFO", "Image captured")
                    CoroutineScope(Dispatchers.IO).launch {
                        scanner.scan(image)
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("ERROR", "Photo capture failed: ${exception.message}", exception)
                }
            })
    }

    override fun onCleared() {
        super.onCleared()
        // Shut down the executor to prevent memory leaks
        analysisExecutor.shutdown()
    }
}