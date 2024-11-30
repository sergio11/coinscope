package com.dreamsoftware.coinscope.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * Data Transfer Object (DTO) representing a cryptocurrency.
 *
 * This class is used to encapsulate data about a specific cryptocurrency,
 * typically received from or sent to an external API.
 *
 * @property id Unique identifier for the cryptocurrency.
 * @property rank The rank of the cryptocurrency in terms of market capitalization.
 * @property name The full name of the cryptocurrency (e.g., "Bitcoin").
 * @property symbol The short symbol or ticker of the cryptocurrency (e.g., "BTC").
 * @property marketCapUsd The market capitalization of the cryptocurrency in US dollars.
 * @property priceUsd The current price of the cryptocurrency in US dollars.
 * @property changePercent24Hr The percentage change in the cryptocurrency's price over the last 24 hours.
 */
@Serializable
data class CoinDTO(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: Double,
    val priceUsd: Double,
    val changePercent24Hr: Double
)
