package com.dreamsoftware.coinscope.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * Data Transfer Object (DTO) representing the historical price data of a cryptocurrency.
 *
 * This class encapsulates a list of price data points for a cryptocurrency,
 * typically retrieved from an external API.
 *
 * @property data A list of historical price data points, each represented by a `CoinPriceDto`.
 */
@Serializable
data class CoinHistoryDTO(
    val data: List<CoinPriceDTO>
)