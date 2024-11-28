package com.dreamsoftware.coinscope.data.networking

import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): com.dreamsoftware.coinscope.domain.util.Result<T, com.dreamsoftware.coinscope.domain.util.NetworkError> {
    return when(response.status.value) {
        in 200..299 -> {
            try {
                com.dreamsoftware.coinscope.domain.util.Result.Success(response.body<T>())
            } catch(e: NoTransformationFoundException) {
                com.dreamsoftware.coinscope.domain.util.Result.Error(com.dreamsoftware.coinscope.domain.util.NetworkError.SERIALIZATION)
            }
        }
        408 -> com.dreamsoftware.coinscope.domain.util.Result.Error(com.dreamsoftware.coinscope.domain.util.NetworkError.REQUEST_TIMEOUT)
        429 -> com.dreamsoftware.coinscope.domain.util.Result.Error(com.dreamsoftware.coinscope.domain.util.NetworkError.TOO_MANY_REQUESTS)
        in 500..599 -> com.dreamsoftware.coinscope.domain.util.Result.Error(com.dreamsoftware.coinscope.domain.util.NetworkError.SERVER_ERROR)
        else -> com.dreamsoftware.coinscope.domain.util.Result.Error(com.dreamsoftware.coinscope.domain.util.NetworkError.UNKNOWN)
    }
}