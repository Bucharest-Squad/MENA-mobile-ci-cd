package net.thechance.mena.dukan.presentation.screen.createProduct

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.attafitamim.krop.core.images.ImageSrc
import io.github.vinceglb.filekit.PlatformFile
import mena.dukan_presentation.generated.resources.Res
import mena.dukan_presentation.generated.resources.add
import mena.dukan_presentation.generated.resources.add_product_
import mena.dukan_presentation.generated.resources.description
import mena.dukan_presentation.generated.resources.ic_alert_circle
import mena.dukan_presentation.generated.resources.ic_arrow_left
import mena.dukan_presentation.generated.resources.ic_price
import mena.dukan_presentation.generated.resources.ic_product
import mena.dukan_presentation.generated.resources.ic_silver_tc
import mena.dukan_presentation.generated.resources.image_1_1
import mena.dukan_presentation.generated.resources.one_selection_for_each_product
import mena.dukan_presentation.generated.resources.price
import mena.dukan_presentation.generated.resources.product_name
import mena.dukan_presentation.generated.resources.shelf
import net.thechance.mena.designsystem.presentation.component.appBar.AppBar
import net.thechance.mena.designsystem.presentation.component.button.PrimaryButton
import net.thechance.mena.designsystem.presentation.component.chip.Chip
import net.thechance.mena.designsystem.presentation.component.icon.Icon
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.component.textField.MultiLineTextField
import net.thechance.mena.designsystem.presentation.component.textField.TextField
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.dukan.presentation.component.DisplayProductImage
import net.thechance.mena.dukan.presentation.component.UploadProductImage
import net.thechance.mena.dukan.presentation.navigation.LocalNavController
import net.thechance.mena.dukan.presentation.screen.createDukan.content.component.SnackBar
import net.thechance.mena.dukan.presentation.screen.cropImage.ImageCropScreen
import net.thechance.mena.dukan.presentation.util.ObserveAsEffect
import net.thechance.mena.dukan.presentation.viewModel.createProduct.CreateProductEffect
import net.thechance.mena.dukan.presentation.viewModel.createProduct.CreateProductInteractionListener
import net.thechance.mena.dukan.presentation.viewModel.createProduct.CreateProductViewModel
import net.thechance.mena.dukan.presentation.viewModel.createProduct.ProductImageUi
import net.thechance.mena.dukan.presentation.viewModel.createProduct.ProductUiState
import net.thechance.mena.dukan.presentation.viewModel.createProduct.ShelfUiState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CreateProductScreen(viewModel: CreateProductViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val navController = LocalNavController.current

    ObserveAsEffect(viewModel.effect) { effect ->
        when (effect) {
            CreateProductEffect.NavigateBack -> navController.navigateUp()
            // Todo Navigate to Mange Product Screen after product added successfully
        }
    }

    CreateProductContent(
        state = state,
        interactionListener = viewModel
    )

    ProductImageCropScreen(
        isVisible = state.showCropImage,
        onCropImageBack = viewModel::onCroppedImage,
        onBack = viewModel::onCropImageBackClick,
        selectedImage = state.selectedImage
    )

    state.snackBarUiState?.let { snackBarState ->
        SnackBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Theme.spacing._16)
                .padding(top = 52.dp)
                .clip(RoundedCornerShape(Theme.radius.md))
                .clickable(onClick = viewModel::onDismissSnackBar),
            isVisible = true,
            onDismiss = viewModel::onDismissSnackBar,
            snackBarUiState = snackBarState
        )
    }

}

@Composable
private fun CreateProductContent(
    state: ProductUiState,
    interactionListener: CreateProductInteractionListener
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Theme.colorScheme.background.surface)
            .statusBarsPadding()
    ) {
        topAppBar(onBackClick = interactionListener::onBackButton)

        productNameSection(
            productName = state.productName,
            onProductNameChange = interactionListener::onProductNameChange
        )

        shelfSection(
            shelves = state.shelves,
            onShelfSelect = interactionListener::onShelfSelect
        )

        priceSection(
            price = state.price,
            onPriceChange = interactionListener::onPriceChange
        )

        descriptionSection(
            description = state.description,
            onDescriptionChange = interactionListener::onDescriptionChange
        )

        imageSection(
            onUploadImageClick = interactionListener::onUploadImageClick,
            onCancelImageClick = interactionListener::onCancelImageClick,
            images = state.images,
            isUploadingImageEnabled = state.isUploadingImageEnabled
        )

        item {
            PrimaryButton(
                text = stringResource(Res.string.add),
                onClick = interactionListener::onAddProductClick,
                isEnabled = state.isAddButtonEnabled,
                isLoading = state.isAddButtonLoading,
                modifier = Modifier
                    .padding(top = 104.dp, bottom = 16.dp)
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = Theme.spacing._16)
            )
        }
    }
}

private fun LazyListScope.topAppBar(onBackClick: () -> Unit) {
    stickyHeader(
        contentType = "topAppBar"
    ) {
        AppBar(
            title = stringResource(resource = Res.string.add_product_),
            titleColor = Theme.colorScheme.shadePrimary,
            modifier = Modifier
                .background(color = Theme.colorScheme.background.surface)
                .padding(top = Theme.spacing._16, bottom = Theme.spacing._8)
                .padding(horizontal = Theme.spacing._16),
            contentPadding = PaddingValues(0.dp),
            leadingContent = {
                Icon(
                    painter = painterResource(resource = Res.drawable.ic_arrow_left),
                    contentDescription = "back icon arrow"
                )
            },
            onLeadingClick = onBackClick,
        )
    }
}

private fun LazyListScope.productNameSection(
    productName: String,
    onProductNameChange: (String) -> Unit
) {
    item {
        Text(
            text = stringResource(Res.string.product_name),
            style = Theme.typography.title.medium,
            modifier = Modifier.padding(top = Theme.spacing._12)
                .padding(horizontal = Theme.spacing._16)


        )
        TextField(
            modifier = Modifier
                .padding(top = Theme.spacing._4)
                .padding(horizontal = Theme.spacing._16)
                .height(48.dp),
            value = productName,
            onValueChanged = onProductNameChange,
            leadingIcon = painterResource(resource = Res.drawable.ic_product),
            leadingIconTint = Theme.colorScheme.shadePrimary,
            hint = ""
        )
    }
}

private fun LazyListScope.shelfSection(
    shelves: List<ShelfUiState>,
    onShelfSelect: (ShelfUiState) -> Unit
) {
    item {
        Text(
            text = stringResource(Res.string.shelf),
            style = Theme.typography.title.medium,
            modifier = Modifier.padding(top = Theme.spacing._12)
                .padding(horizontal = Theme.spacing._16)

        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = Theme.spacing._4)
                .padding(horizontal = Theme.spacing._16),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Theme.spacing._2)
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_alert_circle),
                contentDescription = "shelf alert circle",
                tint = Theme.colorScheme.shadeSecondary
            )

            Text(
                text = stringResource(Res.string.one_selection_for_each_product),
                style = Theme.typography.label.small,
                color = Theme.colorScheme.shadeSecondary
            )
        }

        LazyRow(
            modifier = Modifier
                .padding(start = Theme.spacing._16, top = Theme.spacing._8)
                .fillMaxWidth()
                .height(32.dp),
            horizontalArrangement = Arrangement.spacedBy(Theme.spacing._8),
        ) {
            items(
                items = shelves,
                key = { it.id },
                contentType = { "Shelf" }
            ) { shelf ->
                Chip(
                    text = shelf.name,
                    modifier = Modifier.padding(),
                    isSelected = shelf.isSelected,
                    onClick = { onShelfSelect(shelf) }
                )
            }
        }
    }
}

private fun LazyListScope.priceSection(
    price: String,
    onPriceChange: (String) -> Unit
) {
    item {
        Text(
            text = stringResource(Res.string.price),
            style = Theme.typography.title.medium,
            modifier = Modifier.padding(top = Theme.spacing._12)
                .padding(horizontal = Theme.spacing._16)

        )
        TextField(
            modifier = Modifier.padding(top = Theme.spacing._4)
                .height(48.dp)
                .padding(horizontal = Theme.spacing._16),
            value = price,
            onValueChanged = onPriceChange,
            leadingIcon = painterResource(resource = Res.drawable.ic_price),
            leadingIconTint = Theme.colorScheme.shadePrimary,
            trailingIcon = painterResource(Res.drawable.ic_silver_tc),
            hint = "",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        )
    }
}

private fun LazyListScope.descriptionSection(
    description: String,
    onDescriptionChange: (String) -> Unit
) {
    item {
        Text(
            text = stringResource(Res.string.description),
            style = Theme.typography.title.medium,
            modifier = Modifier.padding(top = Theme.spacing._12)
                .padding(horizontal = Theme.spacing._16)

        )
        MultiLineTextField(
            modifier = Modifier
                .padding(top = Theme.spacing._4)
                .padding(horizontal = Theme.spacing._16)
                .height(96.dp),
            value = description,
            onValueChanged = onDescriptionChange,
            hint = "",
        )
    }
}

private fun LazyListScope.imageSection(
    onUploadImageClick: (image: PlatformFile) -> Unit,
    onCancelImageClick: (image: ImageBitmap) -> Unit,
    isUploadingImageEnabled: Boolean,
    images: List<ProductImageUi>
) {
    item {
        val lazyListImageState = rememberLazyListState()
        var previousSize by remember { mutableStateOf(images.size) }
        LaunchedEffect(images.size) {
            if (images.size > previousSize) {
                lazyListImageState.animateScrollToItem(images.size)
            }
            previousSize = images.size
        }
        Text(
            text = stringResource(Res.string.image_1_1),
            style = Theme.typography.title.medium,
            modifier = Modifier
                .padding(top = Theme.spacing._12)
                .padding(horizontal = Theme.spacing._16)
        )
        LazyRow(
            modifier = Modifier
                .height(108.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Theme.spacing._4),
            contentPadding = PaddingValues(start = Theme.spacing._16, top = Theme.spacing._4, end = Theme.spacing._8),
            reverseLayout = true,
            state = lazyListImageState
        ) {
            items(
                items = images,
                key = { it.id },
                contentType = { "Product Images" }
            ) { image ->

                DisplayProductImage(
                    modifier = Modifier.animateItem(
                        fadeInSpec = tween(durationMillis = 250),
                        fadeOutSpec = tween(durationMillis = 100),
                        placementSpec = spring(
                            stiffness = Spring.StiffnessLow,
                            dampingRatio = Spring.DampingRatioMediumBouncy
                        )
                    ),
                    image = image.image,
                    imageSizeInMegaByte = image.imageSizeInMegaByte,
                    productImageState = image.imageState,
                    onCancelClick = onCancelImageClick,
                    errorMessage = image.errorMessage
                )
            }

            item(
                key = "Upload Product Image Container"
            ) {
                UploadProductImage(
                    modifier = Modifier.size(88.dp),
                    onUploadImageClick = onUploadImageClick,
                    isUploadingImageEnabled = isUploadingImageEnabled
                )
            }
        }
    }
}

@Composable
fun ProductImageCropScreen(
    onCropImageBack: (croppedImage: ImageBitmap) -> Unit,
    onBack: () -> Unit,
    isVisible: Boolean,
    selectedImage: ImageSrc?,
) {
    AnimatedVisibility(
        visible = isVisible,
        modifier = Modifier.fillMaxSize().statusBarsPadding(),
        label = "Crop Product Image"
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = Theme.spacing._16)
                .fillMaxSize()
        ) {
            AppBar(
                title = "Product Image", // Todo ( until the design of cropping product image finish )
                titleColor = Theme.colorScheme.shadePrimary,
                modifier = Modifier
                    .background(color = Theme.colorScheme.background.surface)
                    .padding(bottom = Theme.spacing._16)
                    .padding(horizontal = Theme.spacing._16),
                contentPadding = PaddingValues(0.dp),
                leadingContent = {
                    Icon(
                        painter = painterResource(resource = Res.drawable.ic_arrow_left),
                        contentDescription = "back icon arrow"
                    )
                },
                onLeadingClick = onBack,
            )
            ImageCropScreen(
                selectedImage = selectedImage,
                onImageCrop = onCropImageBack,
                aspectRatio = 1f
            )
        }
    }
}

@Preview
@Composable
private fun CreateProductPreview() {
    MenaTheme {
        CreateProductContent(
            state = ProductUiState(),
            interactionListener = previewInteractionListener
        )
    }
}

private val previewInteractionListener = object : CreateProductInteractionListener {
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