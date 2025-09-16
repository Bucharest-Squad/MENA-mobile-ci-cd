package net.thechance.mena.trends.presentation.screen.interestpick

interface CategoryInteractionListener {
    fun onCategoryClick(categoryId: Int)
    fun onSaveClick()
    fun onBackClick()
}