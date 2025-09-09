package net.thechance.mena.core_chat.data.shared

import net.thechance.mena.core_chat.domain.exception.ChatExceptions

interface BaseRepository {

    suspend fun <T> safeCall(
        maxAttempts: Int = 1,
        call: suspend () -> T,
    ): Result<T> {
        var attempt = 0
        while (attempt < maxAttempts) {
            try {
                return Result.success(call())
            } catch (e: ChatExceptions) {
                return Result.failure(e)
            } catch (e: Exception) {
                attempt++
                if (attempt >= maxAttempts) {
                    return Result.failure(e)
                }
            }
        }
        return Result.failure(Exception("Unknown error"))
    }
}