package net.thechance.mena.identity.data.datautils

import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.SerializationException
import net.thechance.mena.identity.domain.exception.InvalidDataException
import net.thechance.mena.identity.domain.exception.NetworkException
import net.thechance.mena.identity.domain.exception.UnauthorizedException
import net.thechance.mena.identity.domain.exception.UnknownException

suspend fun <T> safeWrapper(function: suspend () -> T): T {
    return try {
        function()
    } catch (e: ClientRequestException) {
        when (e.response.status) {
            HttpStatusCode.Unauthorized -> throw UnauthorizedException()
            else -> throw NetworkException()
        }
    } catch (e: SerializationException) {
        throw InvalidDataException()
    } catch (e: Exception) {
        throw UnknownException()
    }
}