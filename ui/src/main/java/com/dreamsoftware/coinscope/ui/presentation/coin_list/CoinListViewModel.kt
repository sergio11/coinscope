package com.dreamsoftware.coinscope.ui.presentation.coin_list

import com.dreamsoftware.coinscope.domain.model.CoinBO
import com.dreamsoftware.coinscope.domain.usecase.GetCoinsUseCase
import com.dreamsoftware.coinscope.models.CoinVO
import com.dreamsoftware.coinscope.ui.presentation.core.SupportViewModel
import com.dreamsoftware.coinscope.utils.IOneSideMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase,
    private val coinVoMapper: IOneSideMapper<CoinBO, CoinVO>
) : SupportViewModel<CoinListUiState, CoinListSideEffects>() {

    override fun onGetDefaultState(): CoinListUiState = CoinListUiState()

    /*private fun selectCoin(coinVO: CoinVO) {
        _state.update { it.copy(selectedCoin = coinVO) }

        viewModelScope.launch {
            coinDataSource
                .getCoinHistory(
                    coinId = coinVO.id,
                    start = ZonedDateTime.now().minusDays(5),
                    end = ZonedDateTime.now()
                )
                .onSuccess { history ->
                    val dataPointsUI = history
                        .sortedBy { it.dateTime }
                        .map {
                            DataPointVO(
                                x = it.dateTime.hour.toFloat(),
                                y = it.priceUsd.toFloat(),
                                xLabel = DateTimeFormatter
                                    .ofPattern("ha\nM/d")
                                    .format(it.dateTime)
                            )
                        }

                    _state.update {
                        it.copy(
                            selectedCoin = it.selectedCoin?.copy(
                                coinPriceHistory = dataPointsUI
                            )
                        )
                    }
                }
                .onError { error ->
                    _events.send(CoinListSideEffects.Error(error))
                }
        }
    }*/

    fun loadCoins() {
        executeUseCase(
            useCase = getCoinsUseCase,
            onSuccess = ::onLoadCoinsCompleted
        )
    }

    private fun onLoadCoinsCompleted(coinList: List<CoinBO>) {
        updateState { it.copy(coins = coinVoMapper.mapInListToOutList(coinList).toList()) }
    }
}