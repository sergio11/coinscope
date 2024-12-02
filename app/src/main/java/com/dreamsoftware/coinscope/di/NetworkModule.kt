package com.dreamsoftware.coinscope.di

import com.dreamsoftware.coinscope.data.remote.datasource.IRemoteCoinDataSource
import com.dreamsoftware.coinscope.data.remote.datasource.impl.RemoteCoinDataSourceImpl
import com.dreamsoftware.coinscope.ui.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClientEngine(): HttpClientEngine =
        CIO.create()

    @Provides
    @Singleton
    fun provideHttpClient(engine: HttpClientEngine) =
        HttpClient(engine) {
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.ANDROID
            }
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }

    @Provides
    @Singleton
    fun provideRemoteCoinDataSource(
        httpClient: HttpClient,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): IRemoteCoinDataSource =
        RemoteCoinDataSourceImpl(
            httpClient = httpClient,
            dispatcher = dispatcher
        )
}