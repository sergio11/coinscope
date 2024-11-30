package com.dreamsoftware.coinscope.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * Data Transfer Object (DTO) representing a response containing a list of cryptocurrencies.
 *
 * This class is used to encapsulate a collection of cryptocurrency data, typically retrieved
 * from an external API as part of a larger response.
 *
 * @property data A list of cryptocurrencies, where each cryptocurrency is represented by a `CoinDto`.
 */
@Serializable
data class CoinsResponseDTO(
    val data: List<CoinDTO>
)
