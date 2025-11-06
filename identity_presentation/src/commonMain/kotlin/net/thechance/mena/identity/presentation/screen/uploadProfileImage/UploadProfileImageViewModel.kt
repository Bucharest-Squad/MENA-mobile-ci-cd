package net.thechance.mena.identity.presentation.screen.uploadProfileImage

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import net.thechance.mena.identity.domain.exception.AuthenticationException
import net.thechance.mena.identity.domain.model.AuthenticationTokens
import net.thechance.mena.identity.domain.repository.AuthenticationRepository
import net.thechance.mena.identity.domain.repository.CachedImageRepository
import net.thechance.mena.identity.domain.repository.UserRepository
import net.thechance.mena.identity.presentation.base.BaseScreenModel
import net.thechance.mena.identity.presentation.base.error.ErrorState
import net.thechance.mena.identity.presentation.base.error.handleAuthenticationException
import net.thechance.mena.identity.presentation.mapper.mapAuthenticationErrorToMessage
import net.thechance.mena.identity.presentation.mapper.mapErrorToMessage
import net.thechance.mena.identity.presentation.utils.ImageDecoder
import org.jetbrains.compose.resources.StringResource

class UploadProfileImageViewModel(
    private val cachedImageRepository: CachedImageRepository,
    private val userRepository: UserRepository,
    private val imageDecoder: ImageDecoder,
    private val authenticationRepository: AuthenticationRepository,
    private val authTokens: AuthenticationTokens? = null,
    val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : BaseScreenModel<UploadProfileImageUIState, UploadProfileImageUIEffect>
    (UploadProfileImageUIState()),
    UploadProfileImageInteractionListener {

    override fun onClickUpload() {
        val imageBitmap = state.value.imageBitmap ?: return
        val currentAuthTokens = authTokens ?: return

        updateState { copy(isLoading = true, errorMessage = null) }

        tryToExecute(
            function = { 
                withTemporaryTokens(currentAuthTokens) {
                    val imageByteArray = imageDecoder.encodeImage(imageBitmap)
                    userRepository.uploadUserProfileImage(imageByteArray)
                }
            },
            onSuccess = { onUploadSuccess() },
            onError = ::onUploadError,
            dispatcher = dispatcher
        )
    }

    override fun onClickSkip() {
        val currentAuthTokens = authTokens ?: return
        sendNewEffect(UploadProfileImageUIEffect.NavigateToAccountCreated(currentAuthTokens))
    }

    override fun onSelectImage(imageBitmap: ImageBitmap) {
        updateState {
            copy(
                imageBitmap = imageBitmap,
                isUploadEnabled = true
            )
        }
    }

    override fun onClearErrorMessage() {
        updateState { copy(errorMessage = null) }
    }

    private fun onUploadSuccess() {
        val currentAuthTokens = authTokens ?: return
        updateState { copy(isLoading = false) }
        sendNewEffect(UploadProfileImageUIEffect.NavigateToAccountCreated(currentAuthTokens))
    }

    private fun onUploadError(throwable: Throwable) {
        updateState { 
            copy(
                isLoading = false,
                errorMessage = mapErrorMessage(throwable)
            ) 
        }
    }

    override fun onImageCropped(croppedImageBitmap: ImageBitmap) {
        updateState { copy(imageBitmap = croppedImageBitmap, isUploadEnabled = true) }
    }


    override fun onClickEdit(imageBitmap: ImageBitmap) {
        cacheRequiredCropImage(imageBitmap)
    }

    private fun cacheRequiredCropImage(imageBitmap: ImageBitmap) {
        tryToExecute(
            function = {
                cachedImageRepository.cacheImage(
                    IMAGE_KEY,
                    imageDecoder.encodeImage(imageBitmap)
                )
            },
            onSuccess = { handleCacheImageSuccess() },
            onError = ::onCacheCropImageError,
            dispatcher = dispatcher
        )
    }

    private fun handleCacheImageSuccess() {
        sendNewEffect(
            UploadProfileImageUIEffect.NavigateToCropScreen(
                imageKey = IMAGE_KEY,
                onResult = { croppedImageKey ->
                    val imageByteArray = cachedImageRepository.getCachedImage(croppedImageKey)
                    updateState {
                        copy(
                            imageBitmap = imageByteArray?.let { imageDecoder.decodeImage(it) },
                            isUploadEnabled = true
                        )
                    }
                }
            )
        )
    }

    private fun onCacheCropImageError(throwable: Throwable) {
        updateState { copy(errorMessage = mapErrorMessage(throwable)) }
    }

    private fun mapErrorMessage(throwable: Throwable): StringResource {
        return when (throwable) {
            is AuthenticationException -> mapAuthenticationErrorToMessage(
                handleAuthenticationException(throwable)
            )
            else -> mapErrorToMessage(ErrorState.GenericError(throwable))
        }
    }

    private suspend fun <T> withTemporaryTokens(
        authTokens: AuthenticationTokens,
        block: suspend () -> T
    ): T {
        authenticationRepository.saveAuthTokensWithoutEmit(authTokens)
        return try {
            block()
        } finally {
            authenticationRepository.clearAuthTokens()
        }
    }

    private companion object {
        const val IMAGE_KEY = "register"
    }
}