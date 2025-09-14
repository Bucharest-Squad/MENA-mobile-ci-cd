package net.thechance.mena.dukan.presentation.viewModel.cropImage

interface ImageCropInteractionListener {
    fun onUploadAnotherImageClicked()
    fun onZoomInClicked()
    fun onZoomOutClicked()
    fun onResetClicked()
    fun onSaveClicked()
}