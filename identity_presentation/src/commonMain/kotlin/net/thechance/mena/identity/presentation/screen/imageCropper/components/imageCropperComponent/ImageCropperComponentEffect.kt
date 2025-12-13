package net.thechance.mena.identity.presentation.screen.imageCropper.components.imageCropperComponent

sealed interface ImageCropperComponentEffect {
    data class SaveImage(val imageByteArray: ByteArray) : ImageCropperComponentEffect
    data class UploadAnotherImage(val imageByteArray: ByteArray) : ImageCropperComponentEffect
}