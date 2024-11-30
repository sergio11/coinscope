@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package com.dreamsoftware.coinscope.ui.navigation

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dreamsoftware.coinscope.models.CoinVO
import com.dreamsoftware.coinscope.ui.presentation.coin_detail.CoinDetailArgs
import com.dreamsoftware.coinscope.ui.presentation.coin_detail.CoinDetailScreen
import com.dreamsoftware.coinscope.ui.presentation.coin_list.CoinListScreen

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptiveCoinListDetailPane(
    modifier: Modifier = Modifier,
) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            AnimatedPane {
                CoinListScreen(onOpenCoinDetail = { coin ->
                    navigator.navigateTo(
                        pane = ListDetailPaneScaffoldRole.Detail,
                        content = coin
                    )
                })
            }
        },
        detailPane = {
            AnimatedPane {
                navigator.currentDestination?.content?.let { content ->
                    if(content is CoinVO) {
                        CoinDetailScreen(
                            args = CoinDetailArgs(coinVO = content)
                        )
                    }
                }
            }
        },
        modifier = modifier
    )
}