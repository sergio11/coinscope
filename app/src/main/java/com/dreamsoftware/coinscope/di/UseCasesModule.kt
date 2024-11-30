package com.dreamsoftware.coinscope.di

import com.dreamsoftware.coinscope.domain.repository.ICoinRepository
import com.dreamsoftware.coinscope.domain.usecase.GetCoinHistoricalPriceUseCase
import com.dreamsoftware.coinscope.domain.usecase.GetCoinsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCasesModule {

    @Provides
    @ViewModelScoped
    fun provideGetCoinsUseCase(
        coinRepository: ICoinRepository
    ): GetCoinsUseCase =
        GetCoinsUseCase(
            repository = coinRepository,
        )

    @Provides
    @ViewModelScoped
    fun provideGetCoinHistoricalPriceUseCase(
        coinRepository: ICoinRepository
    ): GetCoinHistoricalPriceUseCase =
        GetCoinHistoricalPriceUseCase(
            repository = coinRepository,
        )
}