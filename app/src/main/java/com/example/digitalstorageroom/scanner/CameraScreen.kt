package com.example.digitalstorageroom.scanner

import android.content.Context
import androidx.camera.compose.CameraXViewfinder
import androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
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
import androidx.compose.runtime.remember
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
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    modifier: Modifier = Modifier,
) {
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    if (cameraPermissionState.status.isGranted) {
        CameraContent(modifier)
    } else {
        Column(
            modifier = modifier.fillMaxSize().wrapContentSize().widthIn(max = 480.dp),
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
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val context = LocalContext.current
    // Fix: Use remember to prevent creating a new ViewModel and UseCase on every recomposition
    val viewModel = remember { CameraViewModel() }
    val surfaceRequest by viewModel.surfaceRequest.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel, lifecycleOwner) {
        viewModel.bindToCamera(context.applicationContext, lifecycleOwner)
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

    suspend fun bindToCamera(appContext: Context, lifecycleOwner: LifecycleOwner) {
        val processCameraProvider = ProcessCameraProvider.awaitInstance(appContext)

        // unbindAll before binding to ensure a clean state and avoid TimeoutException
        processCameraProvider.unbindAll()
        
        try {
            processCameraProvider.bindToLifecycle(
                lifecycleOwner, 
                DEFAULT_BACK_CAMERA,
                cameraPreview
            )
            awaitCancellation()
        } finally {
            processCameraProvider.unbindAll()
        }
    }
}