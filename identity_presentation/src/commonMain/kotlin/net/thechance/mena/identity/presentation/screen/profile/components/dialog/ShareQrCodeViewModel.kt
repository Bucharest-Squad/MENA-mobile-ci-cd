package net.thechance.mena.identity.presentation.screen.profile.components.dialog

import androidx.compose.ui.graphics.ImageBitmap
import io.github.vinceglb.filekit.dialogs.compose.util.encodeToByteArray
import mena.identity_presentation.generated.resources.Res
import mena.identity_presentation.generated.resources.cant_save_qr_code
import net.thechance.mena.identity.domain.repository.ImagesRepository
import net.thechance.mena.identity.presentation.base.BaseScreenModel
import net.thechance.mena.identity.presentation.util.permissionHandler.PermissionHandler
import net.thechance.mena.identity.presentation.util.permissionHandler.PermissionState

class ShareQrCodeViewModel(
    private val imagesRepository: ImagesRepository,
    private val galleryPermissionHandler: PermissionHandler
) : BaseScreenModel<ShareQrCodeUIState, ShareQrCodeUIEffect>(
    initialState = ShareQrCodeUIState()
), ShareQrCodeInteractionListener {

    override fun onClickDownload(bitmap: ImageBitmap) {
        val permissionState = galleryPermissionHandler.checkPermission()

        when (permissionState) {
            PermissionState.GRANTED -> {
                tryToExecute(
                    function = { saveImageToGallery(bitmap) },
                    onSuccess = ::onSuccess,
                    onError = ::onError
                )
            }
            PermissionState.DENIED_PERMANENTLY -> galleryPermissionHandler.openSettingPage()
            else -> galleryPermissionHandler.requestPermission()
        }
    }

    private suspend fun saveImageToGallery(bitmap: ImageBitmap) {
        val imageByteArray = bitmap.encodeToByteArray()
        imagesRepository.saveImageToGallery(imageByteArray)
    }

    private fun onSuccess(response: Unit) {
        sendNewEffect(ShareQrCodeUIEffect.OnClickDownload)
    }

    private fun onError(throwable: Throwable) {
        updateState { copy(errorMessage = Res.string.cant_save_qr_code) }
    }
}