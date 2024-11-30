package com.dreamsoftware.coinscope.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * Data Transfer Object (DTO) representing a single price point of a cryptocurrency.
 *
 * This class encapsulates the price of a cryptocurrency at a specific moment in time.
 *
 * @property priceUsd The price of the cryptocurrency in US dollars at the given time.
 * @property time The timestamp of the price point, represented as a Unix epoch time in milliseconds.
 */
@Serializable
data class CoinPriceDTO(
    val priceUsd: Double,
    val time: Long
)