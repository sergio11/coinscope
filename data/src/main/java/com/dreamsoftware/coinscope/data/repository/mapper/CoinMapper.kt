package com.dreamsoftware.coinscope.data.repository.mapper

import com.dreamsoftware.coinscope.data.remote.dto.CoinDTO
import com.dreamsoftware.coinscope.domain.model.CoinBO
import com.dreamsoftware.coinscope.utils.IOneSideMapper

class CoinMapper : IOneSideMapper<CoinDTO, CoinBO> {
    override fun mapInToOut(input: CoinDTO): CoinBO = with(input) {
        CoinBO(
            id = id,
            rank = rank,
            name = name,
            symbol = symbol,
            marketCapUsd = marketCapUsd,
            priceUsd = priceUsd,
            changePercent24Hr = changePercent24Hr
        )
    }

    override fun mapInListToOutList(input: Iterable<CoinDTO>): Iterable<CoinBO> =
        input.map(::mapInToOut)
}