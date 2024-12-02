package com.dreamsoftware.coinscope.ui.presentation.coin_list

import android.content.Context
import com.dreamsoftware.coinscope.domain.exception.CoinsRetrievalException
import com.dreamsoftware.coinscope.ui.R
import com.dreamsoftware.coinscope.ui.presentation.core.IErrorMapper

class CoinListSimpleErrorMapper(
    private val context: Context
) : IErrorMapper {

    override fun mapToMessage(ex: Throwable): String = context.getString(
        when (ex) {
            is CoinsRetrievalException -> R.string.get_coin_list_exception
            else -> R.string.generic_error_exception
        }
    )
}