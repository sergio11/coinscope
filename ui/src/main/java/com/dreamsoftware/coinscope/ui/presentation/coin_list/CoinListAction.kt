package com.dreamsoftware.coinscope.ui.presentation.coin_list

import com.dreamsoftware.coinscope.models.CoinVO


sealed interface CoinListAction {
    data class OnCoinClick(val coinVO: CoinVO): CoinListAction
}