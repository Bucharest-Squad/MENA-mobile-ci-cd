package net.thechance.mena.faith.presentation.base.errorstate

import net.thechance.mena.faith.domain.exception.FaithException
import net.thechance.mena.faith.presentation.base.snackbar.SnackBarState
import org.jetbrains.compose.resources.StringResource

data class ErrorState(
    val message: StringResource,
    val status: SnackBarState.Status = SnackBarState.Status.Error,
    val exception: FaithException? = null
)