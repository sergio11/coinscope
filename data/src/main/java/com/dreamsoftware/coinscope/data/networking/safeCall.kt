package com.dreamsoftware.coinscope.data.networking

import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): com.dreamsoftware.coinscope.domain.util.Result<T, com.dreamsoftware.coinscope.domain.util.NetworkError> {
    val response = try {
        execute()
    } catch(e: UnresolvedAddressException) {
        return com.dreamsoftware.coinscope.domain.util.Result.Error(com.dreamsoftware.coinscope.domain.util.NetworkError.NO_INTERNET)
    } catch(e: SerializationException) {
        return com.dreamsoftware.coinscope.domain.util.Result.Error(com.dreamsoftware.coinscope.domain.util.NetworkError.SERIALIZATION)
    } catch(e: Exception) {
        coroutineContext.ensureActive()
        return com.dreamsoftware.coinscope.domain.util.Result.Error(com.dreamsoftware.coinscope.domain.util.NetworkError.UNKNOWN)
    }

    return responseToResult(response)
}