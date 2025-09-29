package net.thechance.mena.identity.presentation.screen.profile

data class ProfileScreenUIState(
    val fullName:String = "",
    val userName:String = "",
    val profilePicture:String = "",
    val showShareBottomSheet:Boolean = false,
    val showLanguageDialog:Boolean = false,
    val showThemeDialog:Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage:String? = null,
)