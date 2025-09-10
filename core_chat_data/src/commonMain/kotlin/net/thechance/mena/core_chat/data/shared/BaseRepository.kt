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
        try {
            //TODO: handle network connection
            var attempt = 0
            while (attempt < maxAttempts) {
                try {
                    val response = call()
                    return response.getSuccessBodyOrThrow()
                } catch (e: ChatException) {
                    throw e
                } catch (e: Exception) {
                    attempt++
                    if (attempt >= maxAttempts) {
                        throw e
                    }
                }
            }
        } catch (e: ChatException) {
            throw e
        } catch (e: Exception) {
            throw defaultException(e)
        }
        throw UnknownException("This line should never be reached")
    }

    private fun <T> BaseResponseDto<T>.getSuccessBodyOrThrow(): T? {
        return when {
            this.success -> this.body
            this.status == 401 -> throw UnAuthorizedException(this.message)
            else -> throw UnknownException(this.message)
        }
    }
}