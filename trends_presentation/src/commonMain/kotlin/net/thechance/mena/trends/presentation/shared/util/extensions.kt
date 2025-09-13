package net.thechance.mena.trends.presentation.shared.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
fun <T> Flow<T>.throttleFirst(durationMillis: Long): Flow<T> = flow {
    var lastEmissionTime: Instant? = null

    collect { value ->
        val currentTime = Clock.System.now()

        if (lastEmissionTime == null ||
            (currentTime - lastEmissionTime!!).inWholeMilliseconds >= durationMillis
        ) {
            lastEmissionTime = currentTime
            emit(value)
        }
    }
}