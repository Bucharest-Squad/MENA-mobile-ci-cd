package net.thechance.mena.dukan.presentation.viewModel.createProduct

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.SavedStateHandle
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
import net.thechance.mena.dukan.presentation.component.productImage.ProductImageState
import net.thechance.mena.dukan.presentation.screen.createDukan.content.component.SnackBarType
import net.thechance.mena.dukan.presentation.screen.createDukan.content.component.SnackBarUiState
import net.thechance.mena.dukan.presentation.util.imageCrop.toPngByteArray
import net.thechance.mena.dukan.presentation.viewModel.base.BaseViewModel
import kotlin.math.round

class CreateProductViewModel(
    private val productRepository: ProductRepository,
    private val shelfRepository: ShelfRepository,
    savedStateHandle: SavedStateHandle,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : BaseViewModel<ProductUiState, CreateProductEffect>(
    initialState = ProductUiState(),
    defaultDispatcher = dispatcher
), CreateProductInteractionListener {

    init {
        getShelves()
    }

    private val shelfId by lazy {
        savedStateHandle.get<String>(SHELF_ID)
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
            copy(productName = name).updateButtonState()
        }
    }

    override fun onShelfSelect(shelfUiState: ShelfUiState) {
        updateState {
            copy(
                shelves = shelves.map { shelfItem ->
                    shelfItem.copy(isSelected = shelfItem.id == shelfUiState.id)
                },
                selectedShelf = shelfUiState,
            ).updateButtonState()
        }
    }

    override fun onPriceChange(price: String) {
        updateState {
            copy(
                price = price.filter { it.isDigit() || it == PRICE_DECIMAL_SEPARATOR },
            ).updateButtonState()
        }
    }

    override fun onDescriptionChange(description: String) {
        updateState {
            copy(description = description).updateButtonState()
        }
    }

    override fun onUploadImageClick(image: PlatformFile) {
        tryToExecute(
            block = { onUploadImageBlock(image) },
            onError = ::onErrorSnackBar
        )
    }

    private suspend fun onUploadImageBlock(image: PlatformFile) {
        if (isUploadImageValid(image).not())
            awaitCancellation()

        val imageSrc = image.toImageSrc()
        updateState {
            copy(
                selectedImage = imageSrc,
                showCropImage = true
            )
        }
    }

    private suspend fun isUploadImageValid(image: PlatformFile): Boolean {

        val imageSizeInMegabyte = image.size().toDouble() / BYTES_PER_MEGABYTE
        val imageBitmap = image.toImageBitmap()
        val imageAspectRatio = imageBitmap.width.toFloat() / imageBitmap.height.toFloat()
        val imageSrc = image.toImageSrc()

        return when {
            state.value.images.size >= IMAGE_MAX_LIMIT -> {
                updateState {
                    copy(
                        snackBarUiState = SnackBarUiState(
                            message = MESSAGE_IMAGE_MAX_LIMIT_REACHED,
                            snackBarType = SnackBarType.ERROR
                        ),
                        showSnackBar = true,
                    )
                }
                false
            }

            imageSizeInMegabyte > IMAGE_MAX_SIZE_IN_MB -> {
                updateState {
                    copy(
                        snackBarUiState = SnackBarUiState(
                            message = MESSAGE_IMAGE_SIZE_EXCEEDED,
                            snackBarType = SnackBarType.ERROR
                        ),
                        showSnackBar = true,
                        showCropImage = false,
                        selectedImage = null
                    )
                }
                false
            }

            imageAspectRatio == IMAGE_ASPECT_RATIO -> {
                updateState {
                    copy(
                        images = images + ProductImageUi(
                            image = imageBitmap,
                            imageSizeInMegaByte = imageSizeInMegabyte.rounded(),
                            imageState = ProductImageState.SUCCESS,
                        )
                    ).updateButtonState()
                }
                false
            }

            imageSrc == null -> {
                updateState {
                    copy(
                        snackBarUiState = SnackBarUiState(
                            message = MESSAGE_UPLOAD_FAILED,
                            snackBarType = SnackBarType.ERROR
                        ),
                        showSnackBar = true,
                    )
                }
                false
            }

            else -> true
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
        val imageSizeInMegabyte = imageByteArray / BYTES_PER_MEGABYTE

        return ProductImageUi(
            image = imageBitmap,
            imageSizeInMegaByte = imageSizeInMegabyte.rounded(),
            imageState = ProductImageState.SUCCESS,
        )
    }

    private fun onCroppedImageSuccess(productImage: ProductImageUi) {
        updateState {
            copy(images = images + productImage).updateButtonState()
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
            ).updateButtonState()
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
                    message = MESSAGE_ERROR_GENERAL,
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

        val productId = shelfId?.let { myShelfId ->
            productRepository.createProduct(shelfId = myShelfId, product = state.value.toDomain())
        }

        productId?.let { myProductId ->
            productRepository.uploadProductImages(
                fileName = state.value.images.map {
                    state.value.productName +
                            it.image.toPngByteArray().toFileName() +
                            "product_image"
                },
                fileBytes = state.value.images.map { it.image.toPngByteArray() },
                productId = myProductId
            )
        }
    }

    private fun onAddProductSuccess(unit: Unit) {
        updateState {
            copy(
                snackBarUiState = SnackBarUiState(
                    message = MESSAGE_PRODUCT_ADD_SUCCESS,
                    snackBarType = SnackBarType.SUCCESS
                ),
                showSnackBar = true,
                isAddButtonLoading = false,
                images = images.map { it.copy(imageState = ProductImageState.SUCCESS) },
            )
        }
        emitEffect(effect = CreateProductEffect.NavigateToMyDukan)
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
                    message = MESSAGE_ERROR_GENERAL,
                    snackBarType = SnackBarType.ERROR
                ),
                showSnackBar = true,
            )
        }
    }

    private fun ProductUiState.updateButtonState(): ProductUiState {
        return copy(isAddButtonEnabled = isProductValid(this))
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
            productUiState.price.toDoubleOrNull() == null -> VALIDATION_PRICE_INVALID
            productUiState.price.toDouble() < PRICE_EXCLUSIVE_LOWER_BOUND -> VALIDATION_PRICE_NOT_POSITIVE
            productUiState.description.length !in MIN_DESCRIPTION_LENGTH..MAX_DESCRIPTION_LENGTH -> VALIDATION_DESCRIPTION_LENGTH_ERROR
            shelfId == null -> MESSAGE_ERROR_GENERAL
            else -> null
        }
    }

    companion object {
        const val IMAGE_ASPECT_RATIO = 1F
        const val IMAGE_MAX_LIMIT = 9
        const val IMAGE_MAX_SIZE_IN_MB = 5
        const val BYTES_PER_MEGABYTE = 1024 * 1024

        const val MIN_DESCRIPTION_LENGTH = 100
        const val MAX_DESCRIPTION_LENGTH = 3000
        const val PRICE_EXCLUSIVE_LOWER_BOUND = 0.0
        const val PRICE_DECIMAL_SEPARATOR = '.'

        const val SHELF_ID = "shelfId"
        const val MESSAGE_IMAGE_MAX_LIMIT_REACHED =
            "You can upload a maximum of $IMAGE_MAX_LIMIT images."
        const val MESSAGE_IMAGE_SIZE_EXCEEDED =
            "Image size should be less than $IMAGE_MAX_SIZE_IN_MB MB."
        const val MESSAGE_UPLOAD_FAILED = "Upload failed, please try again."
        const val MESSAGE_PRODUCT_ADD_SUCCESS = "Add product successfully."
        const val MESSAGE_ERROR_GENERAL = "Something went wrong, please try again."
        const val VALIDATION_PRICE_INVALID = "Price is invalid."
        const val VALIDATION_PRICE_NOT_POSITIVE =
            "Price must be greater than $PRICE_EXCLUSIVE_LOWER_BOUND."
        const val VALIDATION_DESCRIPTION_LENGTH_ERROR =
            "Description must be between $MIN_DESCRIPTION_LENGTH and $MAX_DESCRIPTION_LENGTH letters."
    }
}

private fun Double.rounded(): Double = (round(this * 100) / 100)
private fun ByteArray.toFileName(): String {
    return this.take(15).joinToString(",") { item -> (item.toInt() and 0xFF).toString() }
}
