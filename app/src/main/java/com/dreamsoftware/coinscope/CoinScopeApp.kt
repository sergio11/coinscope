package com.dreamsoftware.coinscope

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CoinScopeApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}