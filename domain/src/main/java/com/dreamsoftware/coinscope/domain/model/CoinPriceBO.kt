package com.dreamsoftware.coinscope.domain.model

import java.time.ZonedDateTime

data class CoinPriceBO(
    val priceUsd: Double,
    val dateTime: ZonedDateTime
)
