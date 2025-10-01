package net.thechance.mena.dukan.presentation.util.stubPreviews

import androidx.compose.ui.graphics.ImageBitmap
import io.github.vinceglb.filekit.PlatformFile
import net.thechance.mena.dukan.presentation.viewModel.createProduct.CreateProductInteractionListener
import net.thechance.mena.dukan.presentation.viewModel.createProduct.ShelfUiState

object PreviewCreateProductInterfaceListener: CreateProductInteractionListener {
    override fun onBackButton() {
        TODO("Not yet implemented")
    }

    override fun onProductNameChange(name: String) {
        TODO("Not yet implemented")
    }

    override fun onShelfSelect(shelfUiState: ShelfUiState) {
        TODO("Not yet implemented")
    }

    override fun onPriceChange(price: String) {
        TODO("Not yet implemented")
    }

    override fun onDescriptionChange(description: String) {
        TODO("Not yet implemented")
    }

    override fun onUploadImageClick(image: PlatformFile) {
        TODO("Not yet implemented")
    }

    override fun onCancelImageClick(image: ImageBitmap) {
        TODO("Not yet implemented")
    }

    override fun onAddProductClick() {
        TODO("Not yet implemented")
    }

    override fun onDismissSnackBar() {
        TODO("Not yet implemented")
    }

    override fun onCropImageBackClick() {
        TODO("Not yet implemented")
    }
}