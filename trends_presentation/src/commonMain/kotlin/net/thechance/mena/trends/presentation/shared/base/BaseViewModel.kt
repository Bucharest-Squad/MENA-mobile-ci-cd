package net.thechance.mena.trends.presentation.shared.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.thechance.mena.trends.presentation.shared.util.throttleFirst

abstract class BaseViewModel<State, Effect>(
    initialState: State
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<Effect>()
    val effect = _effect.throttleFirst(300L)

    protected fun updateState(updater: State.() -> State) {
        _state.update { updater(it) }
    }

    protected fun sendEffect(
        event: Effect,
        onStart: suspend () -> Unit = {},
        onEnd: suspend () -> Unit = {}
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            onStart()
            _effect.emit(event)
            onEnd()
        }
    }

    protected fun <R> tryToExecute(
        block: suspend () -> R,
        onSuccess: suspend (R) -> Unit,
        onError: suspend (Throwable) -> Unit,
        onStart: suspend () -> Unit = {},
        onEnd: suspend () -> Unit = {},
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): Job {
        return viewModelScope.launch(dispatcher) {
            onStart()
            runCatching { block() }
                .onSuccess { onSuccess(it) }
                .onFailure { onError(it) }
            onEnd()
        }
    }
}