package com.dreamsoftware.coinscope.ui.presentation.coin_list

import com.dreamsoftware.coinscope.domain.model.CoinBO
import com.dreamsoftware.coinscope.domain.usecase.GetCoinsUseCase
import com.dreamsoftware.coinscope.models.CoinVO
import com.dreamsoftware.coinscope.ui.presentation.core.IErrorMapper
import com.dreamsoftware.coinscope.ui.presentation.core.SupportViewModel
import com.dreamsoftware.coinscope.utils.IOneSideMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase,
    private val coinVoMapper: IOneSideMapper<CoinBO, CoinVO>,
    @CoinListScreenErrorMapper private val errorMapper: IErrorMapper,
) : SupportViewModel<CoinListUiState, CoinListSideEffects>(), CoinListScreenActions {

    override fun onGetDefaultState(): CoinListUiState = CoinListUiState()

    fun loadCoins() {
        doOnUiState {
            if(coins.isEmpty()) {
                executeUseCase(
                    useCase = getCoinsUseCase,
                    onSuccess = ::onLoadCoinsCompleted,
                    onMapExceptionToState = ::onMapExceptionToState
                )
            }
        }
    }

    private fun onLoadCoinsCompleted(coinList: List<CoinBO>) {
        updateState { it.copy(coins = coinVoMapper.mapInListToOutList(coinList).toList()) }
    }

    override fun onOpenCoinDetail(coin: CoinVO) {
       launchSideEffect(CoinListSideEffects.OpenCoinDetail(coin))
    }

    private fun onMapExceptionToState(ex: Exception, uiState: CoinListUiState) =
        uiState.copy(
            errorMessage = errorMapper.mapToMessage(ex)
        )
}