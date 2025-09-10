package net.thechance.mena.core_chat.presentation.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class BaseViewModel<S>(initialState: S) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    fun updateState(updater: (S) -> S) = _state.update(updater)

    protected fun <T> tryToExecute(
        execute: suspend () -> T,
        onStart: () -> Unit = {},
        onSuccess: (suspend (T) -> Unit)? = null,
        onError: (Throwable) -> Unit = {},
        coroutineScope: CoroutineScope = viewModelScope,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ): Job {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            onError(throwable)
        }
        return coroutineScope.launch(exceptionHandler + dispatcher) {
            onStart()
            try {
                val result = execute()
                onSuccess?.invoke(result)
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    protected fun <T> tryToCollect(
        collect: () -> Flow<T>,
        onStart: () -> Unit = {},
        onEachEmit: suspend (T?) -> Unit,
        onError: (Throwable) -> Unit = {},
        coroutineScope: CoroutineScope = viewModelScope,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ): Job {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            onError(throwable)
        }

        return coroutineScope.launch(exceptionHandler + dispatcher) {
            try {
                collect()
                    .onStart { onStart() }
                    .catch { onError(it) }
                    .onEmpty { onEachEmit(null) }
                    .collectLatest { onEachEmit(it) }
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}