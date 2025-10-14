package net.thechance.mena.faith.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mena.faith_presentation.generated.resources.Res
import mena.faith_presentation.generated.resources.error_latitude
import mena.faith_presentation.generated.resources.error_longitude
import mena.faith_presentation.generated.resources.error_network
import mena.faith_presentation.generated.resources.error_no_internet
import mena.faith_presentation.generated.resources.error_unauthorized
import mena.faith_presentation.generated.resources.error_unknown
import net.thechance.mena.faith.domain.annotation.KoverIgnore
import net.thechance.mena.faith.domain.exception.FaithException
import net.thechance.mena.faith.presentation.util.ResourceProvider
import net.thechance.mena.faith.presentation.util.StringResourceProvider

@KoverIgnore
abstract class BaseViewModel<UI_STATE, UI_EFFECT>(
    initialState: UI_STATE,
    private val resourceProvider: ResourceProvider = StringResourceProvider()
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _snackBarState = MutableStateFlow(SnackBarState())
    val snackBarState = _snackBarState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<UI_EFFECT>()
    val uiEffect = _uiEffect.asSharedFlow()

    protected fun updateState(updater: (UI_STATE) -> UI_STATE) {
        _uiState.update(updater)
    }

    protected fun sendEffect(effect: UI_EFFECT) {
        viewModelScope.launch {
            _uiEffect.emit(effect)
        }
    }

    fun showSnackBar(
        message: String,
        status: SnackBarState.Status,
        durationMillis: Long = 3000L,
    ) {
        viewModelScope.launch {
            if (snackBarState.value.isVisible) {
                hideSnackBar()
                delay(1000L)
            }
            _snackBarState.update {
                SnackBarState(
                    message = message,
                    status = status,
                    isVisible = true
                )
            }
            delay(durationMillis)
            _snackBarState.update {
                it.copy(
                    isVisible = false
                )
            }
        }
    }

    private fun hideSnackBar() {
        viewModelScope.launch {
            _snackBarState.update {
                it.copy(
                    isVisible = false,
                )
            }
        }
    }

    protected fun <T> tryToExecute(
        execute: suspend () -> T,
        onSuccess: (suspend (T) -> Unit)? = null,
        onError: suspend (Throwable) -> Unit = ::handleError,
        onStart: suspend () -> Unit = {},
        onFinally: () -> Unit = {},
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        inScope: CoroutineScope = viewModelScope,
        delayMillis: Long = 0L
    ): Job {
        return inScope.launch(dispatcher) {
            val handler = CoroutineExceptionHandler { _, throwable ->
                inScope.launch {
                    onError(throwable)
                }
            }
            inScope.launch(dispatcher + handler) {
                onStart()
                delay(delayMillis)
                runCatching { execute() }
                    .onSuccess { result -> onSuccess?.invoke(result) }
                    .onFailure { throwable -> onError(throwable) }
                onFinally()
            }
        }
    }

    private suspend fun handleError(error: Throwable) {
        val faithError = error as? FaithException ?: FaithException.UnknownException
        val message = resourceProvider.getString(faithError.toStringResource())
        showSnackBar(
            message,
            SnackBarState.Status.Error
        )
    }

    private fun FaithException.toStringResource() = when (this) {
        FaithException.NetworkException -> Res.string.error_network
        FaithException.NoInternetException -> Res.string.error_no_internet
        FaithException.UnauthorizedException -> Res.string.error_unauthorized
        FaithException.UnknownException -> Res.string.error_unknown
        FaithException.InvalidLatitudeException -> Res.string.error_latitude
        FaithException.InvalidLongitudeException -> Res.string.error_longitude
    }
}