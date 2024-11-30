package com.dreamsoftware.coinscope.domain.usecase

import com.dreamsoftware.coinscope.domain.model.CoinPriceBO
import com.dreamsoftware.coinscope.domain.repository.ICoinRepository
import com.dreamsoftware.coinscope.domain.usecase.core.SupportUseCaseWithParams
import java.time.ZonedDateTime

class GetCoinHistoricalPriceUseCase(
    private val repository: ICoinRepository
): SupportUseCaseWithParams<GetCoinHistoricalPriceUseCase.Params, List<CoinPriceBO>>() {

    override suspend fun onExecuted(params: Params): List<CoinPriceBO> = with(params) {
        repository.getCoinHistory(
            coinId = coinId,
            start = start,
            end = end
        ).toList()
    }

    data class Params(
        val coinId: String,
        val start: ZonedDateTime,
        val end: ZonedDateTime
    )
}