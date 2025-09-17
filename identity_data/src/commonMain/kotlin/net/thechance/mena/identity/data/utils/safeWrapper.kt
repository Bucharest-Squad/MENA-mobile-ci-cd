package net.thechance.mena.identity.data.utils

import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.SerializationException
import net.thechance.mena.identity.domain.exception.UserNeedsLoginException

suspend fun <T> safeWrapper(function: suspend () -> T): T {
    return try {
        function()
    } catch (e: ClientRequestException) {
        when (e.response.status) {
            HttpStatusCode.Unauthorized -> throw UserNeedsLoginException()
            else -> throw UserNeedsLoginException()
        }
    } catch (e: SerializationException) {
        throw Exception()
    } catch (e: Exception) {
        throw Exception()
    }
}