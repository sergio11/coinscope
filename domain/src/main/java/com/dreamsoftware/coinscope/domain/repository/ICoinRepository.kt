package com.dreamsoftware.coinscope.domain.repository

import com.dreamsoftware.coinscope.domain.exception.CoinHistoryRetrievalException
import com.dreamsoftware.coinscope.domain.exception.CoinsRetrievalException
import com.dreamsoftware.coinscope.domain.model.CoinBO
import com.dreamsoftware.coinscope.domain.model.CoinPriceBO
import java.time.ZonedDateTime

/**
 * Interface for a repository that provides cryptocurrency data and historical data.
 *
 * This repository will interact with the remote data source and handle errors.
 */
interface ICoinRepository {

    /**
     * Retrieves a list of all available cryptocurrencies.
     *
     * @return A list of [CoinBO] objects representing the cryptocurrencies.
     * @throws CoinsRetrievalException If there is an error during the data retrieval process.
     */
    @Throws(CoinsRetrievalException::class)
    suspend fun getCoins(): Iterable<CoinBO>

    /**
     * Retrieves historical price data for a specific cryptocurrency.
     *
     * @param coinId The unique identifier of the cryptocurrency.
     * @param start The start of the time range (ZonedDateTime).
     * @param end The end of the time range (ZonedDateTime).
     * @return A list of [CoinPriceBO] objects representing the cryptocurrency's historical price data.
     * @throws CoinHistoryRetrievalException If there is an error during the data retrieval process.
     */
    @Throws(CoinHistoryRetrievalException::class)
    suspend fun getCoinHistory(coinId: String, start: ZonedDateTime, end: ZonedDateTime): Iterable<CoinPriceBO>
}
