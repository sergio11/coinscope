package com.dreamsoftware.coinscope.di

import android.content.Context
import com.dreamsoftware.coinscope.domain.model.CoinBO
import com.dreamsoftware.coinscope.domain.model.CoinPriceBO
import com.dreamsoftware.coinscope.mapper.CoinVoMapper
import com.dreamsoftware.coinscope.mapper.DataPointVoMapper
import com.dreamsoftware.coinscope.models.CoinVO
import com.dreamsoftware.coinscope.models.DataPointVO
import com.dreamsoftware.coinscope.ui.presentation.coin_detail.CoinDetailScreenErrorMapper
import com.dreamsoftware.coinscope.ui.presentation.coin_detail.CoinDetailSimpleErrorMapper
import com.dreamsoftware.coinscope.ui.presentation.coin_list.CoinListScreenErrorMapper
import com.dreamsoftware.coinscope.ui.presentation.coin_list.CoinListSimpleErrorMapper
import com.dreamsoftware.coinscope.ui.presentation.core.IErrorMapper
import com.dreamsoftware.coinscope.utils.IOneSideMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UiModule {

    @Provides
    @ViewModelScoped
    fun provideCoinVoMapper(): IOneSideMapper<CoinBO, CoinVO> = CoinVoMapper()

    @Provides
    @ViewModelScoped
    fun provideDataPointVoMapper(): IOneSideMapper<CoinPriceBO, DataPointVO> = DataPointVoMapper()

    @Provides
    @ViewModelScoped
    @CoinListScreenErrorMapper
    fun provideCoinListScreenErrorMapper(
        @ApplicationContext context: Context
    ): IErrorMapper =
        CoinListSimpleErrorMapper(context = context)


    @Provides
    @ViewModelScoped
    @CoinDetailScreenErrorMapper
    fun provideCoinDetailScreenErrorMapper(
        @ApplicationContext context: Context
    ): IErrorMapper =
        CoinDetailSimpleErrorMapper(context = context)
}