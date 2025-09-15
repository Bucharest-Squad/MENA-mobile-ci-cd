package net.thechance.mena.dukan.presentation.viewModel.createDukan

import net.thechance.mena.dukan.domain.entity.Category

interface CreateDukanInteractionListener {
    fun onButtonClicked()
    fun onBackClicked()
    fun onClickUploadImage()
    fun onClickEditImage()
    fun onNameChanged(name: String)
    fun isCategorySelected(category: Category): Boolean
    fun onCategorySelected(category: Category): Boolean
    fun onCategoryDeselected(category: Category): Boolean
    fun onCategoryEnabled(category: Category): Boolean
    fun onCLickNext()
    fun onSaveClicked()
    fun onZoomInClicked()
    fun onZoomOutClicked()
    fun onResetClicked()
    fun onUploadAnotherImageClicked()
}