package com.dreamsoftware.coinscope.domain

import com.dreamsoftware.coinscope.domain.model.CoinBO
import com.dreamsoftware.coinscope.domain.model.CoinPrice
import com.dreamsoftware.coinscope.domain.util.NetworkError
import java.time.ZonedDateTime
import com.dreamsoftware.coinscope.domain.util.Result


interface CoinDataSource {
    suspend fun getCoins(): Result<List<CoinBO>, NetworkError>
    suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError>
}