package net.thechance.mena.identity.presentation.screen.uploadProfileImage

sealed interface UploadProfileImageUIEffect {
    data object NavigateToAccountCreated : UploadProfileImageUIEffect
    data class NavigateToCropScreen(
        val imageKey: String,
        val onResult: (String) -> Unit
    ) : UploadProfileImageUIEffect
}