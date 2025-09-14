package net.thechance.mena.identity.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<T, E>(
    initialState: T,
    val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<E>()
    val effect = _effect.asSharedFlow()

    protected fun updateState(updater: T.() -> T) {
        _uiState.update { updater(it) }
    }

    protected fun sendEffect(effect: E) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    protected fun <R> tryToExecute(
        onStart: () -> Unit = {},
        block: suspend () -> R,
        onSuccess: (R) -> Unit = {},
        onError: (exception: Throwable) -> Unit = {},
    ) {
        onStart()
        val handler = createExceptionHandler(onError)
        viewModelScope.launch(defaultDispatcher + handler) {
            val result = block()
            onSuccess(result)
        }
    }

    private fun createExceptionHandler(onError: (Throwable) -> Unit) =
        CoroutineExceptionHandler { _, throwable ->
            onError(throwable)
    }


}