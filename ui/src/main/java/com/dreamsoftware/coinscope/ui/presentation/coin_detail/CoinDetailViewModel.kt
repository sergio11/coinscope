package com.dreamsoftware.coinscope.ui.presentation.coin_detail

import com.dreamsoftware.coinscope.domain.model.CoinPriceBO
import com.dreamsoftware.coinscope.domain.usecase.GetCoinHistoricalPriceUseCase
import com.dreamsoftware.coinscope.models.CoinVO
import com.dreamsoftware.coinscope.models.DataPointVO
import com.dreamsoftware.coinscope.ui.presentation.core.SupportViewModel
import com.dreamsoftware.coinscope.utils.IOneSideMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinHistoricalPriceUseCase: GetCoinHistoricalPriceUseCase,
    private val dataPointMapper: IOneSideMapper<CoinPriceBO, DataPointVO>
) : SupportViewModel<CoinDetailUiState, CoinDetailSideEffects>() {

    override fun onGetDefaultState(): CoinDetailUiState = CoinDetailUiState()

    fun loadCoinDetail(coin: CoinVO) {
        updateState { it.copy(selectedCoin = coin) }
        executeUseCaseWithParams(
            useCase = getCoinHistoricalPriceUseCase,
            params = GetCoinHistoricalPriceUseCase.Params(
                coinId = coin.id,
                start = ZonedDateTime.now().minusDays(5),
                end = ZonedDateTime.now()
            ),
            onSuccess = ::onLoadCoinDetailCompleted
        )
    }

    private fun onLoadCoinDetailCompleted(coinPrices: List<CoinPriceBO>) {
        updateState {
            it.copy(
                selectedCoin = it.selectedCoin?.copy(
                    coinPriceHistory = dataPointMapper.mapInListToOutList(
                        coinPrices.sortedBy(CoinPriceBO::dateTime)
                    ).toList()
                )
            )
        }
    }
}