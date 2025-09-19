package net.thechance.mena.faith.presentation.util

import kotlinx.coroutines.CoroutineDispatcher

expect class AppDispatchers() {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}