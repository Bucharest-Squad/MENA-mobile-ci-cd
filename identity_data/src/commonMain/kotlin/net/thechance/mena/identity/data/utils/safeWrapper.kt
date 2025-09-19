package net.thechance.mena.identity.data.utils

import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.HttpStatusCode
import net.thechance.mena.identity.domain.exception.InvalidCredentialsException
import net.thechance.mena.identity.domain.exception.UnAuthorizedException
import net.thechance.mena.identity.domain.exception.UnknownException
import net.thechance.mena.identity.domain.exception.UserIsBlockedException

suspend fun <T> safeWrapper(block: suspend () -> T): T {
    return try {
        block()
    } catch (e: ClientRequestException) {
        when (e.response.status) {
            HttpStatusCode.Unauthorized -> throw UnAuthorizedException()
            HttpStatusCode.NotFound -> throw InvalidCredentialsException("+00","0000")
            HttpStatusCode.Forbidden -> throw UserIsBlockedException("00000")
            else -> throw UnknownException()
        }
    } catch (e: Exception) {
        throw e
    }
}