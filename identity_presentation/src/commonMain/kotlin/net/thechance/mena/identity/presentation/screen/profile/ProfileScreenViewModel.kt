package net.thechance.mena.identity.presentation.screen.profile

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineScope
import net.thechance.mena.identity.domain.model.User
import net.thechance.mena.identity.domain.repository.UserRepository
import net.thechance.mena.identity.presentation.base.BaseScreenModel
import net.thechance.mena.identity.presentation.base.ErrorState
import net.thechance.mena.identity.presentation.mapper.mapErrorToMessage

class ProfileScreenViewModel(
    private val userRepository: UserRepository
) :
    BaseScreenModel<ProfileScreenUIState, ProfileScreenUIEffect>
        (ProfileScreenUIState()),
    ProfileScreenInteractionListener {
    override val viewModelScope: CoroutineScope get() = screenModelScope

    init {
        getUserInfo()
    }

    private fun onErrorOccurred(errorState: ErrorState) {
        updateState {
            copy(
                isLoading = false,
                errorMessage = mapErrorToMessage(errorState)
            )
        }
    }

    private fun getUserInfo() {
        tryToCollect(
            function = { userRepository.getUser() },
            onNewValue = ::updateUserInfo,
            onError = ::onErrorOccurred,
        )
    }

    private fun updateUserInfo(user: User) {
        updateState {
            copy(
                userName = user.username,
                fullName = user.firstName + "" + user.lastName,
                profileImageUrl = user.profileImageUrl,
                isSuccess = true
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

    override fun clearErrorMessage() {
        updateState { copy(errorMessage = null) }
    }
}