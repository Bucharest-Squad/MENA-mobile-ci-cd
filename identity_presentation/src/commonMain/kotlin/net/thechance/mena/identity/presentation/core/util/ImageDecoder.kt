package net.thechance.mena.identity.presentation.core.util

import androidx.compose.ui.graphics.ImageBitmap

interface ImageDecoder {

    fun decodeImage(byteArray: ByteArray): ImageBitmap

    suspend fun encodeImage(imageBitmap: ImageBitmap): ByteArray

}