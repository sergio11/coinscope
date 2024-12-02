package com.dreamsoftware.coinscope.data.remote.datasource.impl

import com.dreamsoftware.coinscope.data.remote.datasource.IRemoteCoinDataSource
import com.dreamsoftware.coinscope.data.remote.datasource.impl.core.SupportRemoteDataSourceImpl
import com.dreamsoftware.coinscope.data.remote.dto.CoinHistoryDTO
import com.dreamsoftware.coinscope.data.remote.dto.CoinsResponseDTO
import com.dreamsoftware.coinscope.data.remote.exception.NetworkException
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.CoroutineDispatcher
import java.time.ZoneId
import java.time.ZonedDateTime

class RemoteCoinDataSourceImpl(
    private val httpClient: HttpClient,
    dispatcher: CoroutineDispatcher
) : SupportRemoteDataSourceImpl(dispatcher), IRemoteCoinDataSource {

    @Throws(NetworkException::class)
    override suspend fun getCoins(): CoinsResponseDTO = safeCall {
        httpClient.get(urlString = buildFinalUrl("/assets"))
    }

    @Throws(NetworkException::class)
    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): CoinHistoryDTO = safeCall {
        val startMillis = start
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()
        val endMillis = end
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()

        httpClient.get(
            urlString = buildFinalUrl("/assets/$coinId/history")
        ) {
            parameter("interval", "h6")
            parameter("start", startMillis)
            parameter("end", endMillis)
        }
    }
}