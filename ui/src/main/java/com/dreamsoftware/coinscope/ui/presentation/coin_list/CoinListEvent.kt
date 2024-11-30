package com.dreamsoftware.coinscope.ui.presentation.coin_list

import com.dreamsoftware.coinscope.domain.util.NetworkError

sealed interface CoinListEvent {
    data class Error(val error: NetworkError): CoinListEvent
}