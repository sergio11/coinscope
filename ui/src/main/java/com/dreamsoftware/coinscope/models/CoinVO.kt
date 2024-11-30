package com.dreamsoftware.coinscope.models

import androidx.annotation.DrawableRes

data class CoinVO(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: DisplayableNumberVO,
    val priceUsd: DisplayableNumberVO,
    val changePercent24Hr: DisplayableNumberVO,
    @DrawableRes val iconRes: Int,
    val coinPriceHistory: List<DataPointVO> = emptyList()
)

data class DisplayableNumberVO(
    val value: Double,
    val formatted: String
)