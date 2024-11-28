package com.dreamsoftware.coinscope.crypto.presentation.coin_list

import com.dreamsoftware.coinscope.crypto.presentation.models.CoinUi

sealed interface CoinListAction {
    data class OnCoinClick(val coinUi: CoinUi): CoinListAction
}