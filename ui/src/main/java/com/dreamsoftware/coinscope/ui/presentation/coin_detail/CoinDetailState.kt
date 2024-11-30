package com.dreamsoftware.coinscope.ui.presentation.coin_detail

import androidx.compose.runtime.Immutable
import com.dreamsoftware.coinscope.ui.presentation.core.UiState
import com.dreamsoftware.coinscope.models.CoinVO

@Immutable
data class CoinDetailUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val selectedCoin: CoinVO? = null,
): UiState<CoinDetailUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): CoinDetailUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}