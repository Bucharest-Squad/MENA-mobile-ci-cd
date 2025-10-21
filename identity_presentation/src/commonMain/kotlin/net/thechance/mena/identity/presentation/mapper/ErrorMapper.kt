package net.thechance.mena.identity.presentation.mapper

import net.thechance.mena.identity.presentation.base.error.AuthenticationErrorState
import net.thechance.mena.identity.presentation.base.error.LocationErrorState
import net.thechance.mena.identity.presentation.base.error.ProfileErrorState
import org.jetbrains.compose.resources.StringResource

fun mapErrorToMessage(error: Any): StringResource {
    return when (error) {
        is AuthenticationErrorState -> mapAuthenticationErrorToMessage(error)
        is LocationErrorState -> mapLocationErrorToMessage(error)
        is ProfileErrorState -> mapProfileErrorToMessage(error)
        else -> throw IllegalArgumentException("Unknown error type: ${error::class.simpleName}")
    }
}