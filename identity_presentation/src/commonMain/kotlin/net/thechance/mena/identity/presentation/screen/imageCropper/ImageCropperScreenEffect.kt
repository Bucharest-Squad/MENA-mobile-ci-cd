package net.thechance.mena.identity.presentation.screen.imageCropper

import androidx.compose.ui.graphics.ImageBitmap

sealed class ImageCropperScreenEffect {
    object NavigateBackToEditProfile : ImageCropperScreenEffect()
    class NavigateBackToProfile(val imageBytes: ImageBitmap) : ImageCropperScreenEffect()
}