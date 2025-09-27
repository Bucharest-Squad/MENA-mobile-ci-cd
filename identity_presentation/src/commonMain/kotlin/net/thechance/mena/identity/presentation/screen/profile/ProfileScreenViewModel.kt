package net.thechance.mena.identity.presentation.screen.profile

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineScope
import net.thechance.mena.identity.presentation.base.BaseScreenModel
import net.thechance.mena.identity.presentation.base.ErrorState
import net.thechance.mena.identity.presentation.mapper.mapErrorToMessage

class ProfileScreenViewModel() :
    BaseScreenModel<ProfileScreenUIState, ProfileScreenUIEffect>(ProfileScreenUIState()),
    ProfileScreenInteractionListener {
    override val viewModelScope: CoroutineScope get() = screenModelScope

    init {
        updateState {
            copy(
                fullName = "Mohammed Ahmed Mansour",
                userName = "@Mohammed_2025",
                profilePicture = "https://i.pinimg.com/736x/b4/d6/e5/b4d6e50449fff312606a05bce43cc4c3.jpg",
                isSuccess = true,
            )
        }
    }

    private fun onErrorAccrue(errorState: ErrorState) {
        updateState {
            copy(
                isLoading = false,
                errorMessage = mapErrorToMessage(errorState)
            )
        }
    }

    override fun onEditProfileInfoClicked() =
        sendNewEffect(ProfileScreenUIEffect.NavigateToEditProfileScreen)

    override fun onShareClicked() =
        sendNewEffect(ProfileScreenUIEffect.NavigateToEditProfileScreen)

    override fun onInviteFriendsClicked() =
        updateState { copy(showShareBottomSheet = true) }

    override fun onChangePasswordClicked() =
        sendNewEffect(ProfileScreenUIEffect.NavigateToChangePasswordScreen)

    override fun onAddressesClicked() =
        sendNewEffect(ProfileScreenUIEffect.NavigateToLocationPickerScreen)

    override fun onPrivacySettingsClicked() =
        sendNewEffect(ProfileScreenUIEffect.NavigateToPrivacyAndPolicyScreen)

    override fun onLanguageClicked() =
        updateState { copy(showLanguageDialog = true) }

    override fun onThemeClicked() =
        updateState { copy(showThemeDialog = true) }

    override fun onPrivacyAndPolicyClicked() =
        sendNewEffect(ProfileScreenUIEffect.NavigateToPrivacyAndPolicyScreen)

    override fun onContactUsClicked() =
        sendNewEffect(ProfileScreenUIEffect.NavigateContactUsScreen)

    override fun onDismissLanguageDialog() =
        updateState { copy(showLanguageDialog = false) }

    override fun onDismissBottomSheet() =
        updateState { copy(showShareBottomSheet = false) }

    override fun onDismissThemeDialog() =
        updateState { copy(showThemeDialog = false) }

    fun clearErrorMessage() {
        updateState { copy(errorMessage = null) }
    }
}