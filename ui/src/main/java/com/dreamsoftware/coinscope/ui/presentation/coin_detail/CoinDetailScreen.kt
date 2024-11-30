package com.dreamsoftware.coinscope.ui.presentation.coin_detail

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.coinscope.ui.presentation.core.SupportScreen

@Composable
fun CoinDetailScreen(
    viewModel: CoinDetailViewModel = hiltViewModel()
) {
    SupportScreen(
        viewModel = viewModel,
        onInitialUiState = { CoinDetailUiState() }
    ) { uiState ->
        CoinDetailScreenContent(
            uiState = uiState
        )
    }
}