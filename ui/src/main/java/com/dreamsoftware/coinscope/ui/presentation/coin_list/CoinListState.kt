package com.dreamsoftware.coinscope.ui.presentation.coin_list

import androidx.compose.runtime.Immutable
import com.dreamsoftware.coinscope.ui.presentation.core.UiState
import com.dreamsoftware.coinscope.models.CoinVO

@Immutable
data class CoinListUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val coins: List<CoinVO> = emptyList(),
    val selectedCoin: CoinVO? = null
): UiState<CoinListUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): CoinListUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}