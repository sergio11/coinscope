package com.dreamsoftware.coinscope.di

import com.dreamsoftware.coinscope.domain.model.CoinBO
import com.dreamsoftware.coinscope.domain.model.CoinPriceBO
import com.dreamsoftware.coinscope.mapper.CoinVoMapper
import com.dreamsoftware.coinscope.mapper.DataPointVoMapper
import com.dreamsoftware.coinscope.models.CoinVO
import com.dreamsoftware.coinscope.models.DataPointVO
import com.dreamsoftware.coinscope.utils.IOneSideMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
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
}