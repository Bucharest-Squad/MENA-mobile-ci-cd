package net.thechance.mena.wallet.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE, EFFECT>(initialState: STATE) : ViewModel() {
    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<EFFECT>()
    val uiEffect = _uiEffect.asSharedFlow()

    protected val currentState: STATE
        get() = _uiState.value

    protected fun updateState(updater: (STATE) -> STATE) {
        _uiState.update(updater)
    }

    protected fun sendEffect(effect: EFFECT) {
        viewModelScope.launch {
            _uiEffect.emit(effect)
        }
    }

    protected fun <T> tryToExecute(
        callee: suspend () -> T,
        onSuccess: (suspend (T) -> Unit)? = null,
        onError: (suspend (Throwable) -> Unit)? = null,
        onStart: (suspend () -> Unit)? = null,
        onFinish: (suspend () -> Unit)? = null,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ): Job {
        return viewModelScope.launch(dispatcher) {
            try {
                onStart?.invoke()
                runCatching { callee.invoke() }
                    .onSuccess { result -> onSuccess?.invoke(result) }
                    .onFailure { throwable -> onError?.invoke(throwable) }
            } finally {
                onFinish?.invoke()
            }
        }
    }
}