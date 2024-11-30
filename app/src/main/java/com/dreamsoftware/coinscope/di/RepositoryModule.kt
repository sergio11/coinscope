package com.dreamsoftware.coinscope.di

import com.dreamsoftware.coinscope.data.remote.datasource.IRemoteCoinDataSource
import com.dreamsoftware.coinscope.data.remote.dto.CoinDTO
import com.dreamsoftware.coinscope.data.remote.dto.CoinPriceDTO
import com.dreamsoftware.coinscope.data.repository.impl.CoinRepositoryImpl
import com.dreamsoftware.coinscope.data.repository.mapper.CoinMapper
import com.dreamsoftware.coinscope.data.repository.mapper.CoinPriceMapper
import com.dreamsoftware.coinscope.domain.model.CoinBO
import com.dreamsoftware.coinscope.domain.model.CoinPriceBO
import com.dreamsoftware.coinscope.domain.repository.ICoinRepository
import com.dreamsoftware.coinscope.utils.IOneSideMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideCoinMapper(): IOneSideMapper<CoinDTO, CoinBO> = CoinMapper()

    @Provides
    @Singleton
    fun provideCoinPriceMapper(): IOneSideMapper<CoinPriceDTO, CoinPriceBO> = CoinPriceMapper()

    @Provides
    @Singleton
    fun provideCoinRepository(
        remoteCoinDataSource: IRemoteCoinDataSource,
        coinMapper: IOneSideMapper<CoinDTO, CoinBO>,
        coinPriceMapper: IOneSideMapper<CoinPriceDTO, CoinPriceBO>,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): ICoinRepository =
        CoinRepositoryImpl(
            remoteCoinDataSource = remoteCoinDataSource,
            coinMapper = coinMapper,
            coinPriceMapper = coinPriceMapper,
            dispatcher = dispatcher
        )
}