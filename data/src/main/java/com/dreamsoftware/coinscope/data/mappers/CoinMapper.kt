package com.dreamsoftware.coinscope.data.mappers

import com.dreamsoftware.coinscope.data.networking.dto.CoinDto
import com.dreamsoftware.coinscope.data.networking.dto.CoinPriceDto
import com.dreamsoftware.coinscope.domain.model.CoinBO
import com.dreamsoftware.coinscope.domain.model.CoinPrice
import java.time.Instant
import java.time.ZoneId

fun CoinDto.toCoin(): CoinBO {
    return CoinBO(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol,
        marketCapUsd = marketCapUsd,
        priceUsd = priceUsd,
        changePercent24Hr = changePercent24Hr
    )
}

fun CoinPriceDto.toCoinPrice(): CoinPrice {
    return CoinPrice(
        priceUsd = priceUsd,
        dateTime = Instant
            .ofEpochMilli(time)
            .atZone(ZoneId.systemDefault())
    )
}