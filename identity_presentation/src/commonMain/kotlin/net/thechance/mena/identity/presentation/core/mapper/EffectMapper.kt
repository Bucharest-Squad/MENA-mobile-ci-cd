package net.thechance.mena.identity.presentation.core.mapper

import net.thechance.mena.identity.domain.entity.User
import net.thechance.mena.identity.presentation.feature.authentication.login.LoginScreenUIEffect
import net.thechance.mena.identity.presentation.feature.location.locationManagement.LocationManagementScreenUIEffect
import net.thechance.mena.identity.presentation.feature.location.shared.AddressUIState
import net.thechance.mena.identity.presentation.feature.profile.profileMainScreen.ProfileScreenUIEffect

fun createNavigateToMapEffect(
    addressModel: AddressUIState? = null, onSuccess: (AddressUIState) -> Unit
): LocationManagementScreenUIEffect {
    return LocationManagementScreenUIEffect.NavigateToMap(addressModel, onSuccess)
}

fun createNavigateToHomeEffect(): LoginScreenUIEffect {
    return LoginScreenUIEffect.NavigateToHome
}

fun createNavigateToEditProfileEffect(
    userInfo: User?
): ProfileScreenUIEffect {
    return ProfileScreenUIEffect.NavigateToEditProfileScreen(userInfo = userInfo)
}