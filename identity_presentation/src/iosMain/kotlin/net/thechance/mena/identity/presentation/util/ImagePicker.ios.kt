package net.thechance.mena.identity.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.ImageBitmap
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.PhotoResultLauncher
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.dialogs.compose.util.toImageBitmap
import kotlinx.coroutines.launch

actual class CameraImagePicker(val launcher: PhotoResultLauncher) {
    actual fun launch() {
        launcher.launch()
    }
}

@Composable
actual fun rememberCameraPicker(onImagePicked: (ImageBitmap) -> Unit) {
    val scope = rememberCoroutineScope()
    rememberFilePickerLauncher(type = FileKitType.Image) { file ->
        file?.let { image ->
            scope.launch {
                onImagePicked(imageBitmap = image.toImageBitmap())
            }
        }
    }
}