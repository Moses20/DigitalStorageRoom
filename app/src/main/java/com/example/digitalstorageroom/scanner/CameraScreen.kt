package com.example.digitalstorageroom.scanner

import android.Manifest
import android.content.Context
import android.graphics.RectF
import android.provider.ContactsContract
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
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
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
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.math.max
import androidx.compose.ui.geometry.Size as ComposeSize

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    modifier: Modifier = Modifier,
    cameraViewModel: CameraViewModel,
) {
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
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

@Composable
fun CameraContent(
    modifier: Modifier = Modifier,
    cameraViewModel: CameraViewModel,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val context = LocalContext.current
    val surfaceRequest by cameraViewModel.surfaceRequest.collectAsStateWithLifecycle()
    val detectedBarcode by cameraViewModel.detectedBarcode.collectAsStateWithLifecycle()
    val imageSize by cameraViewModel.imageSize.collectAsStateWithLifecycle()
    val imageRotation by cameraViewModel.imageRotation.collectAsStateWithLifecycle()

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
        
        // Draw the barcode bounding box overlay
        BarcodeOverlay(
            barcode = detectedBarcode,
            imageSize = imageSize,
            rotation = imageRotation,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun BarcodeOverlay(
    barcode: Barcode?,
    imageSize: Size?,
    rotation: Int,
    modifier: Modifier = Modifier

    //TODO: Inject DebugProfile
    //This can then be used to debug the barcode overlay, as we do not want it, if it is not in debug mode
) {
    if (barcode == null || imageSize == null) return

    val boundingBox = barcode.boundingBox ?: return
    val textMesurer = rememberTextMeasurer()

    Canvas(modifier = modifier
        .fillMaxSize()
        .drawWithCache{
            val canvasWidth = size.width
            val canvasHeight = size.height
            val imgWidth = imageSize.width
            val imgHeight = imageSize.height

            barcode.cornerPoints

            // 1. Map bounding box to the "upright" coordinate system used by the Viewfinder
            val fRect = RectF(boundingBox)
            /*val mappedRect = when (rotation) {
                90 -> RectF(fRect.left, imgHeight - fRect.top, fRect.right, imgHeight - fRect.bottom)
                180 -> RectF(imgWidth - fRect.right, imgHeight - fRect.bottom, imgWidth - fRect.left, imgHeight - fRect.top)
                270 -> RectF(fRect.top, imgWidth - fRect.right, fRect.bottom, imgWidth - fRect.left)
                else -> fRect
            }*/
            val mappedRect = fRect

            // 2. Calculate scaling and offset for "FillCenter" scale type
            val rotatedWidth = if (rotation % 180 == 90) imgHeight else imgWidth
            val rotatedHeight = if (rotation % 180 == 90) imgWidth else imgHeight

            val scale = max(canvasWidth / rotatedWidth, canvasHeight / rotatedHeight)
            val offsetX = (canvasWidth - rotatedWidth * scale) / 2f
            val offsetY = (canvasHeight - rotatedHeight * scale) / 2f

            val textResult = textMesurer.measure("Hallo: ${barcode.rawValue.toString()}\n" +
                    "Rotation: ${rotation}\n" +
                    "BB Top left: ${boundingBox.left}\n" +
                    "BB Top right: ${boundingBox.right}\n" +
                    "BPoints: ${barcode.cornerPoints?.joinToString("\n")}\n"
            )

            onDrawBehind {
                // 3. Draw the green rectangle
                drawRect(
                    color = Color.Green,
                    topLeft = Offset(mappedRect.left * scale + offsetX, mappedRect.top * scale + offsetY),
                    size = ComposeSize(mappedRect.width() * scale, mappedRect.height() * scale),
                    style = Stroke(width = 3.dp.toPx())
                )


                drawText(
                    textLayoutResult = textResult,
                    color = Color.Green,
                    topLeft = Offset(mappedRect.left * scale + offsetX, mappedRect.top * scale + offsetY),
                )
            }

        }) {

    }
}

class BarcodeScanner {
    private val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_EAN_8, Barcode.FORMAT_EAN_13)
        .build()
    private val scanner = BarcodeScanning.getClient(options)

    @androidx.annotation.OptIn(ExperimentalGetImage::class)
    fun processImage(imageProxy: ImageProxy, onResult: (Barcode?) -> Unit) {
        val mediaImage = imageProxy.image ?: run {
            imageProxy.close()
            return
        }
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        scanner.process(image)
            .addOnSuccessListener { barcodes ->
                onResult(barcodes.maxByOrNull { it.boundingBox?.let { b -> b.width() * b.height() } ?: 0 })
            }
            .addOnFailureListener { onResult(null) }
            .addOnCompleteListener { imageProxy.close() }
    }

    @androidx.annotation.OptIn(ExperimentalGetImage::class)
    suspend fun scan(imageProxy: ImageProxy): Barcode? = suspendCancellableCoroutine { cont ->
        val mediaImage = imageProxy.image ?: run {
            imageProxy.close()
            cont.resume(null)
            return@suspendCancellableCoroutine
        }
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        scanner.process(image)
            .addOnSuccessListener { barcodes ->
                // The barcode with the biggest bounding box is deemed to be the one focused by the user
                cont.resume(barcodes.maxByOrNull { it.boundingBox?.let { b -> b.width() * b.height() } ?: 0 })
            }
            .addOnFailureListener {
                cont.resume(null)
            }
            .addOnCompleteListener { imageProxy.close() }
    }
}

class CodeAnalyzer(private val onBarcodeDetected: (Barcode?, Size, Int) -> Unit) : ImageAnalysis.Analyzer {
    private val scanner = BarcodeScanner()

    @androidx.annotation.OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        scanner.processImage(imageProxy) { barcode ->
            onBarcodeDetected(
                barcode,
                Size(imageProxy.width, imageProxy.height),
                imageProxy.imageInfo.rotationDegrees
            )
        }
    }
}

class CameraViewModel : ViewModel() {
    private val _surfaceRequest = MutableStateFlow<SurfaceRequest?>(null)
    val surfaceRequest: StateFlow<SurfaceRequest?> = _surfaceRequest

    private val _detectedBarcode = MutableStateFlow<Barcode?>(null)
    val detectedBarcode: StateFlow<Barcode?> = _detectedBarcode

    private val _imageSize = MutableStateFlow<Size?>(null)
    val imageSize: StateFlow<Size?> = _imageSize

    private val _imageRotation = MutableStateFlow(0)
    val imageRotation: StateFlow<Int> = _imageRotation

    private val cameraPreview = Preview.Builder().build().apply {
        setSurfaceProvider { _surfaceRequest.value = it }
    }

    private val analysisExecutor = Executors.newSingleThreadExecutor()

    private val analyzer = ImageAnalysis.Builder()
        .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
        .build()
        .also { it.setAnalyzer(analysisExecutor, CodeAnalyzer(::onBarcodeDetected)) }

    private val scanner = BarcodeScanner()
    val imageCapture = ImageCapture.Builder().build()

    private fun onBarcodeDetected(barcode: Barcode?, size: Size, rotation: Int) {
        _detectedBarcode.value = barcode
        _imageSize.value = size
        _imageRotation.value = rotation
    }

    suspend fun bindToCamera(
        appContext: Context,
        lifecycleOwner: LifecycleOwner,
        vararg useCases: UseCase
    ) {
        val processCameraProvider = ProcessCameraProvider.awaitInstance(appContext)
        processCameraProvider.unbindAll()
        try {
            processCameraProvider.bindToLifecycle(
                lifecycleOwner,
                DEFAULT_BACK_CAMERA,
                cameraPreview,
                imageCapture,
                analyzer,
                *useCases
            )
            awaitCancellation()
        } finally {
            processCameraProvider.unbindAll()
        }
    }

    suspend fun takePhoto() {
        imageCapture.takePicture(
            analysisExecutor,
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
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
        analysisExecutor.shutdown()
    }
}
