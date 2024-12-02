package com.dreamsoftware.coinscope.ui.presentation.coin_detail

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.coinscope.models.CoinVO
import com.dreamsoftware.coinscope.ui.presentation.core.SupportScreen

data class CoinDetailArgs(
    val coinVO: CoinVO
)

@Composable
fun CoinDetailScreen(
    args: CoinDetailArgs,
    viewModel: CoinDetailViewModel = hiltViewModel()
) {
    SupportScreen(
        viewModel = viewModel,
        onInitialUiState = { CoinDetailUiState() },
        onInit = {
            loadCoinDetail(args.coinVO)
        }
    ) { uiState ->
        CoinDetailScreenContent(
            uiState = uiState,
            actions = viewModel
        )
    }
}