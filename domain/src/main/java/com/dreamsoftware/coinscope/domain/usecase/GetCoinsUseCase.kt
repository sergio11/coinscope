package com.dreamsoftware.coinscope.domain.usecase

import com.dreamsoftware.coinscope.domain.model.CoinBO
import com.dreamsoftware.coinscope.domain.repository.ICoinRepository
import com.dreamsoftware.coinscope.domain.usecase.core.SupportUseCase

class GetCoinsUseCase(
    private val repository: ICoinRepository
): SupportUseCase<List<CoinBO>>() {

    override suspend fun onExecuted(): List<CoinBO> =
        repository.getCoins().toList()
}