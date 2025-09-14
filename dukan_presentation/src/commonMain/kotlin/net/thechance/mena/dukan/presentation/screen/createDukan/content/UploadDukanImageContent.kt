package net.thechance.mena.dukan.presentation.screen.createDukan.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import mena.dukan_presentation.generated.resources.ImageSize
import mena.dukan_presentation.generated.resources.Res
import mena.dukan_presentation.generated.resources.dukan_image
import mena.dukan_presentation.generated.resources.image_size_description
import net.thechance.mena.designsystem.presentation.component.text.MenaText
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.dukan.presentation.screen.cropImage.ImageCropScreen
import net.thechance.mena.dukan.presentation.screen.cropImage.componetns.UploadImageContainer
import net.thechance.mena.dukan.presentation.util.stubPreviews.PreviewCreateDukanInteractionListener
import net.thechance.mena.dukan.presentation.viewModel.createDukan.CreateDukanInteractionListener
import net.thechance.mena.dukan.presentation.viewModel.createDukan.CreateDukanUiState
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun UploadDukanImageContent(
    state: CreateDukanUiState,
    interactionListener: CreateDukanInteractionListener
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colorScheme.background.surface)
            .padding(horizontal = Theme.spacing._16)
    ) {
        if (state.isImageBeingCropped) {
            item {
                AnimatedVisibility(state.isImageBeingCropped) {
                    ImageCropScreen(
                        selectedImage = state.selectedImage,
                        onImageCrop = { image ->
                            interactionListener.onImageCrop(image)
                        },
                    )
                }
            }
        } else {
            item {
                MenaText(
                    text = stringResource(Res.string.dukan_image),
                    style = Theme.typography.title.medium,
                    color = Theme.colorScheme.shadePrimary,
                )
            }
            item {
                MenaText(
                    text = stringResource(Res.string.image_size_description),
                    style = Theme.typography.body.small,
                    color = Theme.colorScheme.shadeSecondary,
                )
            }
            item {
                MenaText(
                    text = stringResource(Res.string.ImageSize),
                    style = Theme.typography.title.small,
                    color = Theme.colorScheme.shadePrimary,
                    modifier = Modifier.padding(top = Theme.spacing._16, bottom = Theme.spacing._4)
                )
            }
            item {
                UploadImageContainer(
                    onClick = interactionListener::onClickUploadImage,
                    onBottomIconClick = interactionListener::onClickUploadImage,
                    showBottomIcon = state.isEditIconVisible,
                    image = state.croppedImage,
                )
            }
        }
    }
}

@Preview
@Composable
private fun UploadDukanImageContentPreview() {
    MenaTheme {
        UploadDukanImageContent(
            state = CreateDukanUiState(),
            interactionListener = PreviewCreateDukanInteractionListener
        )
    }
}