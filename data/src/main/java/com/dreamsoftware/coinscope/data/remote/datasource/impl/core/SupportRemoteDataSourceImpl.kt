package com.dreamsoftware.coinscope.data.remote.datasource.impl.core

import com.dreamsoftware.coinscope.data.BuildConfig
import com.dreamsoftware.coinscope.data.remote.exception.NetworkError
import com.dreamsoftware.coinscope.data.remote.exception.NetworkException
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException

abstract class SupportRemoteDataSourceImpl(
    protected val dispatcher: CoroutineDispatcher
) {

    companion object {
        const val HTTP_STATUS_OK_MIN = 200
        const val HTTP_STATUS_OK_MAX = 299
        const val HTTP_STATUS_REQUEST_TIMEOUT = 408
        const val HTTP_STATUS_TOO_MANY_REQUESTS = 429
        const val HTTP_STATUS_SERVER_ERROR_MIN = 500
        const val HTTP_STATUS_SERVER_ERROR_MAX = 599
    }

    protected fun buildFinalUrl(url: String): String = when {
        url.contains(BuildConfig.BASE_URL) -> url
        url.startsWith("/") -> BuildConfig.BASE_URL + url.drop(1)
        else -> BuildConfig.BASE_URL + url
    }

    protected suspend inline fun <reified T> safeCall(
        crossinline execute: suspend () -> HttpResponse
    ): T = withContext(dispatcher) {
        val response = try {
            execute()
        } catch (e: UnresolvedAddressException) {
            throw NetworkException(
                error = NetworkError.NO_INTERNET,
                message = "No internet connection available.",
                cause = e
            )
        } catch (e: SerializationException) {
            throw NetworkException(
                error = NetworkError.SERIALIZATION,
                message = "Failed to parse the response.",
                cause = e
            )
        } catch (e: Exception) {
            kotlin.coroutines.coroutineContext.ensureActive()
            throw NetworkException(
                error = NetworkError.UNKNOWN,
                message = "An unknown error occurred.",
                cause = e
            )
        }
        responseToResult(response)
    }

    protected suspend inline fun <reified T : Any> responseToResult(
        response: HttpResponse
    ): T =
        when (response.status.value) {
            in HTTP_STATUS_OK_MIN..HTTP_STATUS_OK_MAX -> {
                try {
                    response.body<T>()
                } catch (e: NoTransformationFoundException) {
                    throw NetworkException(
                        error = NetworkError.SERIALIZATION,
                        message = "Failed to deserialize the response.",
                        cause = e
                    )
                }
            }

            HTTP_STATUS_REQUEST_TIMEOUT -> throw NetworkException(
                error = NetworkError.REQUEST_TIMEOUT,
                message = "The request timed out."
            )

            HTTP_STATUS_TOO_MANY_REQUESTS -> throw NetworkException(
                error = NetworkError.TOO_MANY_REQUESTS,
                message = "Too many requests were sent to the server."
            )

            in HTTP_STATUS_SERVER_ERROR_MIN..HTTP_STATUS_SERVER_ERROR_MAX -> throw NetworkException(
                error = NetworkError.SERVER_ERROR,
                message = "A server-side error occurred. Status: ${response.status.value}."
            )

            else -> throw NetworkException(
                error = NetworkError.UNKNOWN,
                message = "An unexpected HTTP status code was returned: ${response.status.value}."
            )
        }
}