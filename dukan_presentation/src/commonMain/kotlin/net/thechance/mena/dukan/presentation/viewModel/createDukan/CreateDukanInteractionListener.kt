package net.thechance.mena.dukan.presentation.viewModel.createDukan

import net.thechance.mena.dukan.domain.entity.Category
import androidx.compose.ui.graphics.ImageBitmap
import com.attafitamim.krop.core.images.ImageSrc

interface CreateDukanInteractionListener {
    fun onButtonClicked()
    fun onBackClicked()
    fun onClickUploadImage(image: ImageSrc)
    fun onClickEditImage()
    fun onNameChanged(name: String)
    fun isCategorySelected(): (Category) -> Boolean
    fun onCategorySelected(category: Category): Boolean
    fun onCategoryDeselected(category: Category): Boolean
    fun onCategoryEnabled(category: Category): Boolean
    fun onCLickNext()
    fun onImageCrop(image: ImageBitmap)
    fun onCancelCrop()
}