package net.thechance.mena.dukan.presentation.viewModel.createDukan

import net.thechance.mena.dukan.domain.entity.Category

interface CreateDukanInteractionListener {
    fun onButtonClicked()
    fun onBackClicked()
    fun onClickUploadImage()
    fun onClickEditImage()
    fun onNameChanged(name: String)
    fun onCategorySelected(category: Category)
    fun onCategoryDeselected(category: Category)
    fun onCLickNext()
    fun onSaveClicked()
    fun onZoomInClicked()
    fun onZoomOutClicked()
    fun onResetClicked()
    fun onUploadAnotherImageClicked()
}