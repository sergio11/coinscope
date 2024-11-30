package com.dreamsoftware.coinscope.ui.presentation.coin_list

import com.dreamsoftware.coinscope.models.CoinVO
import com.dreamsoftware.coinscope.ui.presentation.core.SideEffect

sealed interface CoinListSideEffects: SideEffect {
    data class OpenCoinDetail(val coin: CoinVO): CoinListSideEffects
}