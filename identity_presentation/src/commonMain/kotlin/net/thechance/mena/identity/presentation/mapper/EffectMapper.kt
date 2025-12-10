package net.thechance.mena.identity.presentation.mapper

import net.thechance.mena.identity.domain.entity.User
import net.thechance.mena.identity.presentation.feature.authentication.login.LoginScreenUIEffect
import net.thechance.mena.identity.presentation.feature.location.addEditLocation.AddEditLocationScreenUIEffect
import net.thechance.mena.identity.presentation.feature.location.shared.AddressUIState
import net.thechance.mena.identity.presentation.feature.profile.mainScreen.ProfileScreenUIEffect

fun createNavigateToMapEffect(
    addressModel: AddressUIState? = null, onSuccess: (AddressUIState) -> Unit
): AddEditLocationScreenUIEffect {
    return AddEditLocationScreenUIEffect.NavigateToMap(addressModel, onSuccess)
}

fun createNavigateToHomeEffect(): LoginScreenUIEffect {
    return LoginScreenUIEffect.NavigateToHome
}

fun createNavigateToEditProfileEffect(
    userInfo: User?
): ProfileScreenUIEffect {
    return ProfileScreenUIEffect.NavigateToEditProfileScreen(userInfo = userInfo)
}