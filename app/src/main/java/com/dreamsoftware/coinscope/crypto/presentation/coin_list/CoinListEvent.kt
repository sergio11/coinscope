package com.dreamsoftware.coinscope.crypto.presentation.coin_list

import com.dreamsoftware.coinscope.domain.util.NetworkError

sealed interface CoinListEvent {
    data class Error(val error: com.dreamsoftware.coinscope.domain.util.NetworkError): CoinListEvent
}