package com.dreamsoftware.coinscope.data.remote.datasource

import com.dreamsoftware.coinscope.data.remote.dto.CoinHistoryDTO
import com.dreamsoftware.coinscope.data.remote.dto.CoinsResponseDTO
import com.dreamsoftware.coinscope.data.remote.exception.NetworkException
import java.time.ZonedDateTime

/**
 * Interface for a remote data source providing cryptocurrency information.
 *
 * This interface defines methods for retrieving cryptocurrency data from a remote API,
 * with specific exception handling using [NetworkException].
 */
interface IRemoteCoinDataSource {

    /**
     * Fetches a list of all available cryptocurrencies.
     *
     * This method performs a remote API call to retrieve a collection of cryptocurrencies.
     * It returns a [CoinsResponseDTO] containing the data of all coins.
     *
     * @return A [CoinsResponseDTO] containing the list of cryptocurrencies retrieved from the API.
     * @throws NetworkException If there is a network-related error during the API call.
     *                          This can include issues such as network timeouts, connectivity problems,
     *                          server errors, or data serialization issues.
     */
    @Throws(NetworkException::class)
    suspend fun getCoins(): CoinsResponseDTO

    /**
     * Fetches the historical price data for a specific cryptocurrency within a given time range.
     *
     * This method performs a remote API call to retrieve the historical price data for a specific cryptocurrency
     * identified by the [coinId]. The time range is defined by [start] and [end] `ZonedDateTime` parameters.
     *
     * @param coinId The unique identifier of the cryptocurrency for which the historical data is to be fetched.
     * @param start The start of the time range for the historical data, specified as a [ZonedDateTime].
     * @param end The end of the time range for the historical data, specified as a [ZonedDateTime].
     * @return A [CoinHistoryDTO] containing the historical data of the cryptocurrency within the specified time range.
     * @throws NetworkException If there is a network-related error during the API call.
     *                          This can include issues such as network timeouts, connectivity problems,
     *                          server errors, or data serialization issues.
     */
    @Throws(NetworkException::class)
    suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): CoinHistoryDTO
}
