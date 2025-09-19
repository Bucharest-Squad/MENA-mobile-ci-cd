package net.thechance.mena.identity.data.utils

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.http.HttpStatusCode
import net.thechance.mena.identity.domain.exception.InvalidCredentialsException
import net.thechance.mena.identity.domain.exception.UnAuthorizedException
import net.thechance.mena.identity.domain.exception.UnknownException
import net.thechance.mena.identity.domain.exception.UserIsBlockedException

suspend fun <T> safeWrapper(block: suspend () -> T): T {
    return try {
        block()
    } catch (e: ServerResponseException) {
        when (e.response.status) {
            HttpStatusCode.Unauthorized -> throw UnAuthorizedException()
            HttpStatusCode.NotFound -> throw InvalidCredentialsException("+20", "01234567")
            HttpStatusCode.Forbidden -> throw UserIsBlockedException("01234567")
            else -> throw UnknownException()
        }
    } catch (e: Exception) {
        throw e
    }
}