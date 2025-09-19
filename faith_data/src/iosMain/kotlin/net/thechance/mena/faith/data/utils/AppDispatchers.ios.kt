package net.thechance.mena.faith.data.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual class DataDispatchers actual constructor() {
    actual val main: CoroutineDispatcher get() = Dispatchers.Main
    actual val io: CoroutineDispatcher get() = Dispatchers.Default
    actual val default: CoroutineDispatcher get() = Dispatchers.Default
    actual val unconfined: CoroutineDispatcher get() = Dispatchers.Unconfined
}