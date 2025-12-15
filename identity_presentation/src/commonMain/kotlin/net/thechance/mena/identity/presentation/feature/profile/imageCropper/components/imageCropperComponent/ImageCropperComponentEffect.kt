package net.thechance.mena.identity.presentation.feature.profile.imageCropper.components.imageCropperComponent

sealed interface ImageCropperComponentEffect {
    data class SaveImage(val imageByteArray: ByteArray) : ImageCropperComponentEffect
    data class UploadAnotherImage(val imageByteArray: ByteArray) : ImageCropperComponentEffect
}