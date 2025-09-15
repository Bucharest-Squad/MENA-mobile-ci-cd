package net.thechance.mena.dukan.presentation.viewModel.cropImage

import androidx.compose.ui.graphics.ImageBitmap
import com.attafitamim.krop.core.crop.CropError
import com.attafitamim.krop.core.crop.CropResult
import com.attafitamim.krop.core.crop.crop
import com.attafitamim.krop.core.images.ImageSrc
import net.thechance.mena.dukan.presentation.viewModel.base.BaseViewModel

class ImageCropViewModel() : BaseViewModel<
        ImageCropUiState, ImageCropEffects>(
    ImageCropUiState()
), ImageCropInteractionListener {


    fun onSelectImage(imageSrc: ImageSrc?) {
        println("hello")
        tryToExecute(block = {
            state.value.cropper.let { cropper ->

                when (val result = cropper.crop(imageSrc)) {
                    CropError.LoadingError -> {
                        println("Loading Error")
                    }

                    CropError.SavingError -> {
                        println("Saving Error")
                    }

                    CropResult.Cancelled -> {
                        println("Cancelled")
                    }

                    is CropResult.Success -> {
                        println("Success")
                        onCropImageSuccess(result.bitmap)
                    }
                }
            }
        }
        )
    }


    override fun onSaveClicked() {
        state.value.cropper.cropState?.done(true)
    }

    override fun onUploadAnotherImageClicked(
        imageSrc: ImageSrc?
    ) {
        onSelectImage(imageSrc)
    }

    override fun onZoomInClicked() {
        TODO("Not yet implemented")
    }

    override fun onZoomOutClicked() {
        TODO("Not yet implemented")
    }

    override fun onResetClicked() {
        state.value.cropper.cropState?.reset()
    }

    private fun onCropImageSuccess(selectedImage: ImageBitmap) {
        emitEffect(ImageCropEffects.NavigateBack(selectedImage))
    }
}
