package net.thechance.mena.dukan.presentation.util.stubPreviews

import net.thechance.mena.dukan.domain.entity.Category
import net.thechance.mena.dukan.presentation.viewModel.createDukan.CreateDukanInteractionListener

object PreviewCreateDukanInteractionListener : CreateDukanInteractionListener {
    override fun onButtonClicked() {}
    override fun onBackClicked() {}
    override fun onClickUploadImage() {}
    override fun onClickEditImage() {}
    override fun onNameChanged(name: String) {}
    override fun onCategorySelected(category: Category) {}
    override fun onCategoryDeselected(category: Category) {}
    override fun onCLickNext() {}
    override fun onSaveClicked() {}
    override fun onZoomInClicked() {}
    override fun onZoomOutClicked() {}
    override fun onResetClicked() {}
    override fun onUploadAnotherImageClicked() {}
}