package com.dreamsoftware.coinscope.ui.presentation.coin_list

import com.dreamsoftware.coinscope.ui.presentation.models.CoinUi


sealed interface CoinListAction {
    data class OnCoinClick(val coinUi: CoinUi): CoinListAction
}