package com.dreamsoftware.coinscope

import android.app.Application
import com.dreamsoftware.coinscope.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CoinScopeApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CoinScopeApp)
            androidLogger()

            modules(appModule)
        }
    }
}