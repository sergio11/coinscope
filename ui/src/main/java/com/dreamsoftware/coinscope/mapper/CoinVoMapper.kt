package com.dreamsoftware.coinscope.mapper

import com.dreamsoftware.coinscope.domain.model.CoinBO
import com.dreamsoftware.coinscope.models.CoinVO
import com.dreamsoftware.coinscope.ui.presentation.core.extensions.symbolToCoinDrawable
import com.dreamsoftware.coinscope.ui.presentation.core.extensions.toDisplayableNumber
import com.dreamsoftware.coinscope.utils.IOneSideMapper

class CoinVoMapper: IOneSideMapper<CoinBO, CoinVO> {
    override fun mapInToOut(input: CoinBO): CoinVO = with(input) {
        CoinVO(
            id = id,
            name = name,
            symbol = symbol,
            rank = rank,
            priceUsd = priceUsd.toDisplayableNumber(),
            marketCapUsd = marketCapUsd.toDisplayableNumber(),
            changePercent24Hr = changePercent24Hr.toDisplayableNumber(),
            iconRes = symbol.symbolToCoinDrawable()
        )
    }

    override fun mapInListToOutList(input: Iterable<CoinBO>): Iterable<CoinVO> =
        input.map(::mapInToOut)
}