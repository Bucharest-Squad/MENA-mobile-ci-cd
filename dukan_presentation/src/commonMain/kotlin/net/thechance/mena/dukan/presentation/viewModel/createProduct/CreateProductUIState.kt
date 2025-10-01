package net.thechance.mena.dukan.presentation.viewModel.createProduct

import androidx.compose.ui.graphics.ImageBitmap
import com.attafitamim.krop.core.images.ImageSrc
import net.thechance.mena.dukan.presentation.component.productImage.ProductImageState
import net.thechance.mena.dukan.presentation.screen.createDukan.content.component.SnackBarUiState
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class ProductUiState(
    val isAddButtonLoading: Boolean = false,
    val isAddButtonEnabled: Boolean = false,
    val shelves: List<ShelfUiState> = emptyList(),
    val selectedShelf: ShelfUiState? = null,
    val productName: String = "",
    val price: String = "",
    val description: String = "",
    val images: List<ProductImageUi> = emptyList(),
    val selectedImage: ImageSrc? = null,
    val isUploadingImageEnabled: Boolean = true,
    val showCropImage:Boolean=false,
    val snackBarUiState: SnackBarUiState? = null,
    val showSnackBar: Boolean = false,
)

data class ShelfUiState(
    val id:String="",
    val name: String = "",
    val isSelected: Boolean = false,
)

@OptIn(ExperimentalTime::class)
data class ProductImageUi(
    val id: Long = Clock.System.now().toEpochMilliseconds(),
    val image: ImageBitmap,
    val imageSizeInMegaByte: Double,
    val imageState: ProductImageState,
    val errorMessage: String? = null,
)