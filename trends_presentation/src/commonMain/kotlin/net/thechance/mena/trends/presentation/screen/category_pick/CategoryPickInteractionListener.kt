package net.thechance.mena.trends.presentation.screen.category_pick

interface CategoryPickInteractionListener {
    fun onCategoryClick(categoryId: Int)
    fun onSaveClick()
    fun onBackClick()
}