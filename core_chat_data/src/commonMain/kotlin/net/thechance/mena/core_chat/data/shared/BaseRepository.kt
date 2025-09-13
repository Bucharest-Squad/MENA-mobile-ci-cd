package net.thechance.mena.core_chat.data.shared

import com.bilalazzam.contacts_provider.ContactsPermissionDeniedException as ContactsProviderPermissionDeniedException
import com.bilalazzam.contacts_provider.FetchContactsFailedException
import net.thechance.mena.core_chat.data.shared.dto.BaseResponseDto
import net.thechance.mena.core_chat.domain.exception.ChatException
import net.thechance.mena.core_chat.domain.exception.ContactsFetchFailedException
import net.thechance.mena.core_chat.domain.exception.ContactsPermissionDeniedException
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
        runCatchingWithException(defaultException) {
            //TODO: handle network connection
            var attempt = 0
            while (attempt < maxAttempts) {
                try {
                    val response = call()
                    return@runCatchingWithException response.getSuccessBodyOrThrow()
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
        throw UnknownException("This line should never be reached")
    }

    private suspend fun <T> runCatchingWithException(
        defaultException: (Throwable) -> ChatException,
        block: suspend () -> T
    ): T {
        try {
            return block()
        } catch (e: ContactsProviderPermissionDeniedException) {
            throw ContactsPermissionDeniedException("Contacts Permission Denied!", e)
        } catch (e: FetchContactsFailedException) {
            throw ContactsFetchFailedException("Failed to fetch Contacts!", e)
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