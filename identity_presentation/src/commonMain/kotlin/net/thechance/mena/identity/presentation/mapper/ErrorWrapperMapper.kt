package net.thechance.mena.identity.presentation.mapper

import net.thechance.mena.identity.presentation.base.error.ErrorState
import mena.identity_presentation.generated.resources.Res
import mena.identity_presentation.generated.resources.error_something_went_wrong
import org.jetbrains.compose.resources.StringResource

internal fun mapErrorToMessage(error: ErrorState): StringResource {
    return when (error) {
        is ErrorState.AuthenticationError -> mapAuthenticationErrorToMessage(error.error)
        is ErrorState.LocationError -> mapLocationErrorToMessage(error.error)
        is ErrorState.ProfileError -> mapProfileErrorToMessage(error.error)
        is ErrorState.GenericError -> { Res.string.error_something_went_wrong }
    }
}