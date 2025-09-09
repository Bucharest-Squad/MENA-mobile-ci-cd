package net.thechance.mena.core_chat.data.shared

import net.thechance.mena.core_chat.data.contacts.source.remote.BaseResponseDto
import net.thechance.mena.core_chat.domain.exception.ChatExceptions
import net.thechance.mena.core_chat.domain.exception.UnAuthorizedException

interface BaseRepository {

    suspend fun <T> safeNetworkCall(
        maxAttempts: Int = 1,
        call: suspend () -> BaseResponseDto<T>,
    ): Result<T?> {
        //TODO: handle network connection
        var attempt = 0
        while (attempt < maxAttempts) {
            try {
                val response = call()
                return if (response.success) {
                    Result.success(response.body)
                } else if (response.status == 401) {
                    Result.failure(UnAuthorizedException(response.message))
                } else {
                    Result.failure(ChatExceptions(response.message ?: "Unknown error"))
                }
            } catch (e: ChatExceptions) {
                return Result.failure(e)
            } catch (e: Exception) {
                attempt++
                if (attempt >= maxAttempts) {
                    return Result.failure(e)
                }
            }
        }
        return Result.failure(ChatExceptions("Unknown error"))
    }
}