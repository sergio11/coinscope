package com.dreamsoftware.coinscope.data.repository.mapper

import com.dreamsoftware.coinscope.data.remote.dto.CoinPriceDTO
import com.dreamsoftware.coinscope.domain.model.CoinPriceBO
import com.dreamsoftware.coinscope.utils.IOneSideMapper
import java.time.Instant
import java.time.ZoneId

class CoinPriceMapper : IOneSideMapper<CoinPriceDTO, CoinPriceBO> {
    override fun mapInToOut(input: CoinPriceDTO): CoinPriceBO = with(input) {
        CoinPriceBO(
            priceUsd = priceUsd,
            dateTime = Instant
                .ofEpochMilli(time)
                .atZone(ZoneId.systemDefault())
        )
    }


    override fun mapInListToOutList(input: Iterable<CoinPriceDTO>): Iterable<CoinPriceBO> =
        input.map(::mapInToOut)
}