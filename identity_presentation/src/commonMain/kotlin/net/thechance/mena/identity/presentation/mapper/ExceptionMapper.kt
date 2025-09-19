package net.thechance.mena.identity.presentation.mapper

import net.thechance.mena.identity.presentation.base.ErrorState

internal fun mapErrorToMessage(error: ErrorState): String {
    return when (error) {
        ErrorState.InvalidCountryCode -> "Invalid country code"
        ErrorState.InvalidMobileNumber -> "Invalid mobile number"
        ErrorState.InvalidPassword -> "Invalid password"
        ErrorState.UserIsBlockedException -> "User is blocked"
        is ErrorState.WrongPassword -> error.message
        ErrorState.LocationPermissionDenied -> "Location permission denied"
        is ErrorState.SomethingWentWrong -> "Something went wrong"
    }
}
