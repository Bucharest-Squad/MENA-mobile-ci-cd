package net.thechance.mena.dukan.presentation.viewModel.createProduct

import androidx.compose.ui.graphics.ImageBitmap
import com.attafitamim.krop.filekit.toImageSrc
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.compose.util.toImageBitmap
import io.github.vinceglb.filekit.size
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.awaitCancellation
import net.thechance.mena.dukan.domain.entity.Shelf
import net.thechance.mena.dukan.domain.repository.ProductRepository
import net.thechance.mena.dukan.domain.repository.ShelfRepository
import net.thechance.mena.dukan.presentation.component.ProductImageState
import net.thechance.mena.dukan.presentation.screen.createDukan.content.component.SnackBarType
import net.thechance.mena.dukan.presentation.screen.createDukan.content.component.SnackBarUiState
import net.thechance.mena.dukan.presentation.util.imageCrop.toPngByteArray
import net.thechance.mena.dukan.presentation.viewModel.base.BaseViewModel
import kotlin.math.round

class CreateProductViewModel(
    private val productRepository: ProductRepository,
    private val shelfRepository: ShelfRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel<ProductUiState, CreateProductEffect>(
    initialState = ProductUiState(),
    defaultDispatcher = dispatcher
), CreateProductInteractionListener {

    init {
        getShelves()
    }

    private fun getShelves() {
        tryToExecute(
            block = shelfRepository::getMyDukanShelves,
            onSuccess = ::onGetShelvesSuccess,
            onError = ::onErrorSnackBar
        )
    }

    private fun onGetShelvesSuccess(shelves: List<Shelf>) {
        updateState {
            copy(shelves = shelves.map { it.toUiState() })
        }
    }

    override fun onBackButton() {
        emitEffect(effect = CreateProductEffect.NavigateBack)
    }

    override fun onProductNameChange(name: String) {
        updateState {
            copy(
                productName = name
            ).also { updatedState ->
                updateState { copy(isAddButtonEnabled = isProductValid(updatedState)) }
            }
        }
    }

    override fun onShelfSelect(shelfUiState: ShelfUiState) {
        updateState {
            copy(
                shelves = shelves.map { shelfItem ->
                    shelfItem.copy(isSelected = shelfItem.id == shelfUiState.id)
                },
                selectedShelf = shelfUiState,
            ).also { updatedState ->
                updateState { copy(isAddButtonEnabled = isProductValid(updatedState)) }
            }
        }
    }

    override fun onPriceChange(price: String) {
        updateState {
            copy(
                price = price.filter { it.isDigit() || it == '.' },
            ).also { updatedState ->
                updateState { copy(isAddButtonEnabled = isProductValid(updatedState)) }
            }
        }
    }

    override fun onDescriptionChange(description: String) {
        updateState {
            copy(
                description = description,
            ).also { updatedState ->
                updateState { copy(isAddButtonEnabled = isProductValid(updatedState)) }
            }
        }
    }

    override fun onUploadImageClick(image: PlatformFile) {
        tryToExecute(
            block = { onUploadImageBlock(image) },
            onError = ::onErrorSnackBar
        )
    }

    private suspend fun onUploadImageBlock(image: PlatformFile) {
        if (state.value.images.size > 9) {
            updateState {
                copy(
                    snackBarUiState = SnackBarUiState(
                        message = "You can upload only 10 images",
                        snackBarType = SnackBarType.ERROR
                    ),
                    showSnackBar = true,
                )
            }
            awaitCancellation()
        }

        val imageSizeInMegabyte = image.size().toDouble() / (1024.0 * 1024.0)
        if (imageSizeInMegabyte > 5) {
            updateState {
                copy(
                    snackBarUiState = SnackBarUiState(
                        message = "You can upload only 10 images",
                        snackBarType = SnackBarType.ERROR
                    ),
                    showSnackBar = true,
                    showCropImage = false,
                    selectedImage = null
                )
            }
            awaitCancellation()
        }

        val imageBitmap = image.toImageBitmap()
        val imageAspectRatio = imageBitmap.width.toFloat() / imageBitmap.height.toFloat()
        if (imageAspectRatio == 1f) {
            updateState {
                copy(
                    images = images + ProductImageUi(
                        image = imageBitmap,
                        imageSizeInMegaByte = imageSizeInMegabyte.rounded(),
                        imageState = ProductImageState.SUCCESS,
                    )
                ).also { updatedState ->
                    updateState { copy(isAddButtonEnabled = isProductValid(updatedState)) }
                }
            }
            awaitCancellation()
        }

        val imageSrc = image.toImageSrc()
        updateState {
            copy(
                selectedImage = imageSrc,
                showCropImage = true
            )
        }
    }

    fun onCroppedImage(imageBitmap: ImageBitmap) {
        tryToExecute(
            block = { onCroppedImageBlock(imageBitmap) },
            onSuccess = ::onCroppedImageSuccess,
            onError = ::onErrorSnackBar
        )
    }

    private fun onCroppedImageBlock(imageBitmap: ImageBitmap): ProductImageUi {
        updateState {
            copy(
                selectedImage = null,
                showCropImage = false
            )
        }

        val imageByteArray = imageBitmap.toPngByteArray().size.toDouble()
        val imageSizeInMegabyte = imageByteArray / (1024.0 * 1024.0)

        return ProductImageUi(
            image = imageBitmap,
            imageSizeInMegaByte = imageSizeInMegabyte.rounded(),
            imageState = ProductImageState.SUCCESS,
        )
    }

    private fun onCroppedImageSuccess(productImage: ProductImageUi) {
        updateState {
            copy(
                images = images + productImage,
            ).also { updatedState ->
                updateState { copy(isAddButtonEnabled = isProductValid(updatedState)) }
            }
        }
    }

    override fun onCropImageBackClick() {
        updateState {
            copy(
                selectedImage = null,
                showCropImage = false
            )
        }
    }

    override fun onCancelImageClick(image: ImageBitmap) {
        updateState {
            copy(
                images = images.filter { it.image != image }
            )
        }
    }

    override fun onAddProductClick() {
        tryToExecute(
            block = ::onAddProductBlock,
            onSuccess = ::onAddProductSuccess,
            onError = ::onAddProductError
        )
    }

    private fun onAddProductError(throwable: Throwable) {
        updateState {
            copy(
                snackBarUiState = SnackBarUiState(
                    message = throwable.message.orEmpty(),
                    snackBarType = SnackBarType.ERROR
                ),
                showSnackBar = true,
                isAddButtonLoading = false,
                isUploadingImageEnabled = true
            )
        }
    }

    private suspend fun onAddProductBlock() {
        val productErrorMessage = getProductValidationError(productUiState = state.value)
        if (productErrorMessage != null) {
            updateState {
                copy(
                    snackBarUiState = SnackBarUiState(
                        message = productErrorMessage,
                        snackBarType = SnackBarType.ERROR
                    ),
                    showSnackBar = true,
                )
            }
            awaitCancellation()
        }

        updateState {
            copy(
                images = images.map { it.copy(imageState = ProductImageState.LOADING) },
                isAddButtonLoading = true,
                isUploadingImageEnabled = false
            )
        }

        // Todo ( add a new functions of create product and upload image here )
//         val productId = productRepository.createProduct(state.value.toDomain())
//                productRepository.uploadProductImages(
//                    fileName = state.value.images.map {
//                                state.value.productName +
//                                it.image.toPngByteArray().toFileName() +
//                                "product_image"
//                      },
//                    fileBytes = state.value.images.map { it.image.toPngByteArray() },
//                    productId = productId
//                )
    }

    private fun onAddProductSuccess(unit: Unit) {
        updateState {
            copy(
                snackBarUiState = SnackBarUiState(
                    message = "Add product successfully.",
                    snackBarType = SnackBarType.SUCCESS
                ),
                showSnackBar = true,
                isAddButtonLoading = false,
                images = images.map { it.copy(imageState = ProductImageState.SUCCESS) },
            )
        }
        // Todo emit effect Navigate to Mange Product Screen
    }

    override fun onDismissSnackBar() {
        updateState {
            copy(
                showSnackBar = false,
                snackBarUiState = null
            )
        }
    }

    private fun onErrorSnackBar(throwable: Throwable) {
        updateState {
            copy(
                snackBarUiState = SnackBarUiState(
                    message = throwable.message.orEmpty(),
                    snackBarType = SnackBarType.ERROR
                ),
                showSnackBar = true,
            )
        }
    }

    private fun isProductValid(productUiState: ProductUiState): Boolean {
        return when {
            productUiState.productName.trim().isEmpty() -> false
            productUiState.selectedShelf == null -> false
            productUiState.price.isEmpty() -> false
            productUiState.description.trim().isEmpty() -> false
            productUiState.images.map { it.image }.isEmpty() -> false
            else -> true
        }
    }

    private fun getProductValidationError(productUiState: ProductUiState): String? {
        return when {
            productUiState.price.toDoubleOrNull() == null -> "Price is invalid"
            productUiState.price.toDouble() <= 0 -> "Price must be greater than zero"

            productUiState.description.length !in 100..3000 -> "Description must be between 100 and 3000 letters"
            else -> null
        }
    }
}

private fun Double.rounded(): Double = (round(this * 100) / 100)
private fun ByteArray.toFileName(): String {
    return this.take(15).joinToString(",") { item -> (item.toInt() and 0xFF).toString() }
}


