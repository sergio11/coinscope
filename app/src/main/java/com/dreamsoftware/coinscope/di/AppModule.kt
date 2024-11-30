package com.dreamsoftware.coinscope.di

import com.dreamsoftware.coinscope.data.networking.HttpClientFactory
import com.dreamsoftware.coinscope.data.networking.RemoteCoinDataSource
import com.dreamsoftware.coinscope.domain.CoinDataSource
import com.dreamsoftware.coinscope.ui.presentation.coin_list.CoinListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    singleOf(::RemoteCoinDataSource).bind<CoinDataSource>()

    viewModelOf(::CoinListViewModel)
}