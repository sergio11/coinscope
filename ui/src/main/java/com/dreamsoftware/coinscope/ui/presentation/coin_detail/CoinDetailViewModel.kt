package com.dreamsoftware.coinscope.ui.presentation.coin_detail

import com.dreamsoftware.coinscope.domain.model.CoinPriceBO
import com.dreamsoftware.coinscope.domain.usecase.GetCoinHistoricalPriceUseCase
import com.dreamsoftware.coinscope.models.DataPointVO
import com.dreamsoftware.coinscope.ui.presentation.core.SupportViewModel
import com.dreamsoftware.coinscope.utils.IOneSideMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinHistoricalPriceUseCase: GetCoinHistoricalPriceUseCase,
    private val dataPointMapper: IOneSideMapper<CoinPriceBO, DataPointVO>
) : SupportViewModel<CoinDetailUiState, CoinDetailSideEffects>() {

    override fun onGetDefaultState(): CoinDetailUiState = CoinDetailUiState()

}