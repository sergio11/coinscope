package com.dreamsoftware.coinscope.data.repository.impl

import com.dreamsoftware.coinscope.data.remote.datasource.IRemoteCoinDataSource
import com.dreamsoftware.coinscope.data.remote.dto.CoinDTO
import com.dreamsoftware.coinscope.data.remote.dto.CoinPriceDTO
import com.dreamsoftware.coinscope.data.remote.exception.NetworkException
import com.dreamsoftware.coinscope.data.repository.impl.core.SupportRepositoryImpl
import com.dreamsoftware.coinscope.domain.exception.CoinHistoryRetrievalException
import com.dreamsoftware.coinscope.domain.exception.CoinsRetrievalException
import com.dreamsoftware.coinscope.domain.model.CoinBO
import com.dreamsoftware.coinscope.domain.model.CoinPriceBO
import com.dreamsoftware.coinscope.domain.repository.ICoinRepository
import com.dreamsoftware.coinscope.utils.IOneSideMapper
import kotlinx.coroutines.CoroutineDispatcher
import java.time.ZonedDateTime

/**
 * Implementation of the CoinRepository that interacts with the remote data source.
 *
 * This implementation retrieves cryptocurrency data from a remote API and handles exceptions
 * related to network failures and invalid input.
 */
class CoinRepositoryImpl(
    private val remoteCoinDataSource: IRemoteCoinDataSource,
    private val coinMapper: IOneSideMapper<CoinDTO, CoinBO>,
    private val coinPriceMapper: IOneSideMapper<CoinPriceDTO, CoinPriceBO>,
    dispatcher: CoroutineDispatcher
) : SupportRepositoryImpl(dispatcher), ICoinRepository {

    @Throws(CoinsRetrievalException::class)
    override suspend fun getCoins(): Iterable<CoinBO> = safeExecute {
        try {
            remoteCoinDataSource
                .getCoins()
                .data
                .let(coinMapper::mapInListToOutList)
        } catch (ex: NetworkException) {
            throw CoinsRetrievalException("Error retrieving coins data", ex)
        }
    }

    @Throws(CoinHistoryRetrievalException::class)
    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Iterable<CoinPriceBO> = safeExecute {
        try {
            remoteCoinDataSource
                .getCoinHistory(coinId, start, end)
                .data
                .let(coinPriceMapper::mapInListToOutList)
        } catch (ex: NetworkException) {
            throw CoinHistoryRetrievalException("Error retrieving coin history data", ex)
        }
    }
}