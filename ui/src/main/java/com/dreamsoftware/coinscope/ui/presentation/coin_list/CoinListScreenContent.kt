package com.dreamsoftware.coinscope.ui.presentation.coin_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.dreamsoftware.coinscope.models.CoinVO
import com.dreamsoftware.coinscope.ui.presentation.coin_list.components.CoinListItem
import com.dreamsoftware.coinscope.ui.presentation.coin_list.components.previewCoinBO
import com.dreamsoftware.coinscope.ui.theme.CoinScopeTheme

@Composable
internal fun CoinListScreenContent(
    uiState: CoinListUiState,
    actions: CoinListScreenActions
) {
    with(uiState) {
        if(isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(coins) { coinUi ->
                    CoinListItem(
                        coinVO = coinUi,
                        onClick = {
                            actions.onOpenCoinDetail(coin = coinUi)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun CoinListScreenPreview() {
    CoinScopeTheme {
        CoinListScreenContent(
            uiState = CoinListUiState(
                coins = (1..100).map {
                    previewCoinBO.copy(id = it.toString())
                }
            ),
            actions = object : CoinListScreenActions {
                override fun onOpenCoinDetail(coin: CoinVO) {}
            }
        )
    }
}