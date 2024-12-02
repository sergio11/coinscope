package com.dreamsoftware.coinscope.ui.presentation.coin_detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dreamsoftware.coinscope.models.ChartStyle
import com.dreamsoftware.coinscope.models.DataPointVO
import com.dreamsoftware.coinscope.ui.R
import com.dreamsoftware.coinscope.ui.presentation.coin_detail.components.InfoCard
import com.dreamsoftware.coinscope.ui.presentation.coin_detail.components.LineChart
import com.dreamsoftware.coinscope.ui.presentation.coin_list.components.previewCoinBO
import com.dreamsoftware.coinscope.ui.presentation.core.LCEScreenContent
import com.dreamsoftware.coinscope.ui.presentation.core.extensions.toDisplayableNumber
import com.dreamsoftware.coinscope.ui.theme.CoinScopeTheme
import com.dreamsoftware.coinscope.ui.theme.greenBackground

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun CoinDetailScreenContent(
    uiState: CoinDetailUiState,
    actions: CoinDetailScreenActions
) {
    with(uiState) {
        LCEScreenContent(
            uiState = uiState,
            onRetryClicked = actions::onRetry
        ) {
            val contentColor = if (isSystemInDarkTheme()) {
                Color.White
            } else {
                Color.Black
            }
            if(selectedCoin != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = selectedCoin.iconRes
                        ),
                        contentDescription = selectedCoin.name,
                        modifier = Modifier.size(100.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = selectedCoin.name,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center,
                        color = contentColor
                    )
                    Text(
                        text = selectedCoin.symbol,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Center,
                        color = contentColor
                    )
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        InfoCard(
                            title = stringResource(id = R.string.market_cap),
                            formattedText = "$ ${selectedCoin.marketCapUsd.formatted}",
                            icon = ImageVector.vectorResource(R.drawable.stock)
                        )
                        InfoCard(
                            title = stringResource(id = R.string.price),
                            formattedText = "$ ${selectedCoin.priceUsd.formatted}",
                            icon = ImageVector.vectorResource(R.drawable.dollar)
                        )
                        val absoluteChangeFormatted =
                            (selectedCoin.priceUsd.value * (selectedCoin.changePercent24Hr.value / 100))
                                .toDisplayableNumber()
                        val isPositive = selectedCoin.changePercent24Hr.value > 0.0
                        val contentColor = if (isPositive) {
                            if (isSystemInDarkTheme()) Color.Green else greenBackground
                        } else {
                            MaterialTheme.colorScheme.error
                        }
                        InfoCard(
                            title = stringResource(id = R.string.change_last_24h),
                            formattedText = absoluteChangeFormatted.formatted,
                            icon = if (isPositive) {
                                ImageVector.vectorResource(id = R.drawable.trending)
                            } else {
                                ImageVector.vectorResource(id = R.drawable.trending_down)
                            },
                            contentColor = contentColor
                        )
                    }
                    AnimatedVisibility(
                        visible = selectedCoin.coinPriceHistory.isNotEmpty()
                    ) {
                        var selectedDataPointVO by remember {
                            mutableStateOf<DataPointVO?>(null)
                        }
                        var labelWidth by remember {
                            mutableFloatStateOf(0f)
                        }
                        var totalChartWidth by remember {
                            mutableFloatStateOf(0f)
                        }
                        val amountOfVisibleDataPoints = if (labelWidth > 0) {
                            ((totalChartWidth - 2.5 * labelWidth) / labelWidth).toInt()
                        } else {
                            0
                        }
                        val startIndex = (selectedCoin.coinPriceHistory.lastIndex - amountOfVisibleDataPoints)
                            .coerceAtLeast(0)
                        LineChart(
                            dataPointVOS = selectedCoin.coinPriceHistory,
                            style = ChartStyle(
                                chartLineColor = MaterialTheme.colorScheme.primary,
                                unselectedColor = MaterialTheme.colorScheme.secondary.copy(
                                    alpha = 0.3f
                                ),
                                selectedColor = MaterialTheme.colorScheme.primary,
                                helperLinesThicknessPx = 5f,
                                axisLinesThicknessPx = 5f,
                                labelFontSize = 14.sp,
                                minYLabelSpacing = 25.dp,
                                verticalPadding = 8.dp,
                                horizontalPadding = 8.dp,
                                xAxisLabelSpacing = 8.dp
                            ),
                            visibleDataPointsIndices = startIndex..selectedCoin.coinPriceHistory.lastIndex,
                            unit = "$",
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16 / 9f)
                                .onSizeChanged { totalChartWidth = it.width.toFloat() },
                            selectedDataPointVO = selectedDataPointVO,
                            onSelectedDataPoint = {
                                selectedDataPointVO = it
                            },
                            onXLabelWidthChange = { labelWidth = it }
                        )
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun CoinDetailScreenPreview() {
    CoinScopeTheme {
        CoinDetailScreenContent(
            uiState = CoinDetailUiState(
                selectedCoin = previewCoinBO,
            ),
            actions = object: CoinDetailScreenActions {
                override fun onRetry() {}
            }
        )
    }
}