package net.thechance.mena.core_chat.data.shared

import net.thechance.mena.core_chat.data.contacts.source.remote.BaseResponseDto
import net.thechance.mena.core_chat.domain.exception.ChatException

interface BaseRepository {

    suspend fun <T> tryNetworkCall(
        maxAttempts: Int = 1,
        call: suspend () -> BaseResponseDto<T>,
    ): T? {
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
        throw ChatException("This line should never be reached")
    }

    suspend fun <T> runCatchingWithException(
        exceptionBuilder: (Throwable) -> Exception,
        block: suspend () -> T
    ): T {
        try {
            return block()
        } catch (e: ChatException) {
            throw e
        } catch (e: Exception) {
            throw exceptionBuilder(e)
        }
    }
}