package net.thechance.mena.faith.presentation.util

import kotlinx.coroutines.CoroutineDispatcher

import kotlinx.coroutines.Dispatchers

actual class AppDispatchers actual constructor() {
    actual val main: CoroutineDispatcher get() = Dispatchers.Main
    actual val io: CoroutineDispatcher get() = Dispatchers.Default
    actual val default: CoroutineDispatcher get() = Dispatchers.Default
    actual val unconfined: CoroutineDispatcher get() = Dispatchers.Unconfined
}