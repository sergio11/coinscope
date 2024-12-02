package com.dreamsoftware.coinscope.ui.presentation.coin_list

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.coinscope.models.CoinVO
import com.dreamsoftware.coinscope.ui.presentation.core.SupportScreen

@Composable
fun CoinListScreen(
    viewModel: CoinListViewModel = hiltViewModel(),
    onOpenCoinDetail: (CoinVO) -> Unit
) {
    SupportScreen(
        viewModel = viewModel,
        onInitialUiState = { CoinListUiState() },
        onInit = {
            loadCoins()
        },
        onSideEffect = {
            when(it) {
                is CoinListSideEffects.OpenCoinDetail -> onOpenCoinDetail(it.coin)
            }
        },
        onResume = {
            loadCoins(forceLoad = true)
        }
    ) { uiState ->
        CoinListScreenContent(
            uiState = uiState,
            actions = viewModel
        )
    }
}

