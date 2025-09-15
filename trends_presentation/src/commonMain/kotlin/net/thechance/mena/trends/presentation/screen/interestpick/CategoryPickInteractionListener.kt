package net.thechance.mena.trends.presentation.screen.interestpick

interface CategoryPickInteractionListener {
    fun onCategoryClick(categoryId: Int)
    fun onSaveClick()
    fun onBackClick()
}