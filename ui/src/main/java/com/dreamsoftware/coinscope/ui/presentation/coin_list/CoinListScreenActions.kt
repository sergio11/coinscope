package com.dreamsoftware.coinscope.ui.presentation.coin_list

import com.dreamsoftware.coinscope.models.CoinVO
import com.dreamsoftware.coinscope.ui.presentation.core.IScreenActionListener

interface CoinListScreenActions: IScreenActionListener {
    fun onOpenCoinDetail(coin: CoinVO)
}