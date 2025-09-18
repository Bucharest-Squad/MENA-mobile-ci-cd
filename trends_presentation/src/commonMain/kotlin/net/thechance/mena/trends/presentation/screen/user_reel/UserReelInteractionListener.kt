package net.thechance.mena.trends.presentation.screen.user_reel

interface UserReelInteractionListener {
    fun onBackClick()
    fun onDeleteClick()
    fun onConfirmDeleteClick()
    fun onDismissDialog()
    fun onDescriptionClick(isCollapsed: Boolean)
}