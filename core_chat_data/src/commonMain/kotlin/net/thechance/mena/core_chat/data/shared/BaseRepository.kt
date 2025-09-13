package net.thechance.mena.core_chat.data.shared

import net.thechance.mena.core_chat.data.shared.dto.BaseResponseDto
import net.thechance.mena.core_chat.domain.exception.ChatException
import net.thechance.mena.core_chat.domain.exception.UnAuthorizedException
import net.thechance.mena.core_chat.domain.exception.UnknownException

interface BaseRepository {

    suspend fun <T> tryNetworkCall(
        defaultException: (Throwable) -> ChatException = { e ->
            UnknownException("Unknown error occurred", e)
        },
        maxAttempts: Int = 1,
        call: suspend () -> BaseResponseDto<T>,
    ): T? {
        var response: T? = null
        runCatchingWithException(defaultException) {
            //TODO: handle network connection
            var attempt = 0
            while (attempt < maxAttempts) {
                try {
                    response = call().getSuccessBodyOrThrow()
                    return@runCatchingWithException
                } catch (e: ChatException) {
                    throw e
                } catch (e: Exception) {
                    attempt++
                    if (attempt >= maxAttempts) {
                        throw e
                    }
                }
            }
        }
        return response
    }

    private suspend fun <T> runCatchingWithException(
        defaultException: (Throwable) -> ChatException,
        block: suspend () -> T
    ): T {
        try {
            return block()
        } catch (e: ChatException) {
            throw e
        } catch (e: Exception) {
            throw defaultException(e)
        }
    }

    private fun <T> BaseResponseDto<T>.getSuccessBodyOrThrow(): T? {
        return when {
            this.success -> this.body
            this.status == STATUS_UNAUTHORIZED -> throw UnAuthorizedException(this.message)
            //TODO: handle more cases like 500, 404
            else -> throw UnknownException(this.message)
        }
    }

    companion object {
        private const val STATUS_UNAUTHORIZED = 401
    }
}