package net.thechance.mena.dukan.data.repository.util

import kotlinx.io.IOException
import net.thechance.mena.dukan.domain.exceptions.DukanException
import net.thechance.mena.dukan.domain.exceptions.NoInternetException

suspend fun <T> safeApiCall(
    block: suspend () -> T,
): T {
    return runCatching { block() }
        .getOrElse { e ->
            when (e) {
                is IOException -> throw NoInternetException()
                else -> throw DukanException()
            }
        }
}