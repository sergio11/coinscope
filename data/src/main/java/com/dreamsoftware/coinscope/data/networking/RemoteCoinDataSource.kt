package com.dreamsoftware.coinscope.data.networking

import com.dreamsoftware.coinscope.data.mappers.toCoin
import com.dreamsoftware.coinscope.data.mappers.toCoinPrice
import com.dreamsoftware.coinscope.data.networking.dto.CoinHistoryDto
import com.dreamsoftware.coinscope.data.networking.dto.CoinsResponseDto
import com.dreamsoftware.coinscope.domain.CoinDataSource
import com.dreamsoftware.coinscope.domain.model.CoinBO
import com.dreamsoftware.coinscope.domain.model.CoinPrice
import com.dreamsoftware.coinscope.domain.util.NetworkError
import com.dreamsoftware.coinscope.domain.util.map
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.ZoneId
import java.time.ZonedDateTime
import com.dreamsoftware.coinscope.domain.util.Result

class RemoteCoinDataSource(
    private val httpClient: HttpClient
): CoinDataSource {

    override suspend fun getCoins(): Result<List<CoinBO>, NetworkError> {
        return safeCall<CoinsResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets")
            )
        }.map { response ->
            response.data.map { it.toCoin() }
        }
    }

    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError> {
        val startMillis = start
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()
        val endMillis = end
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()

        return safeCall<CoinHistoryDto> {
            httpClient.get(
                urlString = constructUrl("/assets/$coinId/history")
            ) {
                parameter("interval", "h6")
                parameter("start", startMillis)
                parameter("end", endMillis)
            }
        }.map { response ->
            response.data.map { it.toCoinPrice() }
        }
    }
}