package net.thechance.mena.dukan.presentation.viewModel.createProduct

import androidx.compose.ui.graphics.ImageBitmap
import io.github.vinceglb.filekit.PlatformFile

interface CreateProductInteractionListener {
    fun onBackButton()
    fun onProductNameChange(name: String)
    fun onShelfSelect(shelfUiState: ShelfUiState)
    fun onPriceChange(price: String)
    fun onDescriptionChange(description: String)
    fun onUploadImageClick(image: PlatformFile)
    fun onCancelImageClick(image: ImageBitmap)
    fun onAddProductClick()
    fun onDismissSnackBar()
    fun onCropImageBackClick()
}
