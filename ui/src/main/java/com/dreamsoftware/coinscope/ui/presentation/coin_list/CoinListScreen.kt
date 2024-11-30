package com.dreamsoftware.coinscope.ui.presentation.coin_list

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.coinscope.ui.presentation.core.SupportScreen

@Composable
fun CoinListScreen(
    viewModel: CoinListViewModel = hiltViewModel(),
) {
    SupportScreen(
        viewModel = viewModel,
        onInitialUiState = { CoinListUiState() },
        onInit = {
            loadCoins()
        }
    ) { uiState ->
        CoinListScreenContent(uiState = uiState)
    }
}

