package net.thechance.mena.trends.data.util

import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.io.IOException
import net.thechance.mena.trends.domain.exception.NoInternetException

suspend inline fun <reified T> safeApiCall(
    execute: suspend () -> HttpResponse
): T {
    val result = try {
        execute()
    } catch (_: IOException) {
        throw NoInternetException()
    } catch (_: UnresolvedAddressException) {
        throw NoInternetException()
    } catch (exception: Exception) {
        throw exception
    }

    return handleErrorStatus(result)
}

suspend inline fun <reified T> handleErrorStatus(result: HttpResponse): T {
    TODO()
//    return when (result.status.value) {
//        in 200..299 -> {
//            result.body<T>()
//        }
//
//        in 400..499 -> {
//            when (result.status) {
//                HttpStatusCode.Unauthorized -> {
//                    TODO()
//                }
//                HttpStatusCode.NotFound -> {
//                    TODO()
//                }
//                else ->
//            }
//        }
//
//        in 500..599 -> {
//            TODO()
//        }
//        else -> {
//            TODO()
//        }
//    }
}