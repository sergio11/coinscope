package com.dreamsoftware.coinscope.mapper

import com.dreamsoftware.coinscope.domain.model.CoinPriceBO
import com.dreamsoftware.coinscope.models.DataPointVO
import com.dreamsoftware.coinscope.utils.IOneSideMapper
import java.time.format.DateTimeFormatter

class DataPointVoMapper: IOneSideMapper<CoinPriceBO, DataPointVO> {
    override fun mapInToOut(input: CoinPriceBO): DataPointVO = with(input) {
        DataPointVO(
            x = dateTime.hour.toFloat(),
            y = priceUsd.toFloat(),
            xLabel = DateTimeFormatter
                .ofPattern("ha\nM/d")
                .format(dateTime)
        )
    }

    override fun mapInListToOutList(input: Iterable<CoinPriceBO>): Iterable<DataPointVO> =
        input.map(::mapInToOut)
}