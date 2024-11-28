package com.dreamsoftware.coinscope.crypto.presentation.coin_list

import com.dreamsoftware.coinscope.core.domain.util.NetworkError

sealed interface CoinListEvent {
    data class Error(val error: NetworkError): CoinListEvent
}