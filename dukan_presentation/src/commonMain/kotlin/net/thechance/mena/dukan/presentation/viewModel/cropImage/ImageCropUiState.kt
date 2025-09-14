package net.thechance.mena.dukan.presentation.viewModel.cropImage

import com.attafitamim.krop.core.crop.ImageCropper
import com.attafitamim.krop.core.crop.imageCropper

data class ImageCropUiState(
    val cropper: ImageCropper = imageCropper(),
    val isZoomInEnabled: Boolean = false,
    val isZoomOutEnabled: Boolean = false
)