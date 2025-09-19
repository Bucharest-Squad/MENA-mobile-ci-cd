package net.thechance.mena.faith.data.utils

import kotlinx.coroutines.CoroutineDispatcher

expect class DataDispatchers() {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}