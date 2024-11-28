package com.dreamsoftware.coinscope.core.presentation.util

import android.content.Context
import com.dreamsoftware.coinscope.R
import com.dreamsoftware.coinscope.domain.util.NetworkError

fun com.dreamsoftware.coinscope.domain.util.NetworkError.toString(context: Context): String {
    val resId = when(this) {
        com.dreamsoftware.coinscope.domain.util.NetworkError.REQUEST_TIMEOUT -> R.string.error_request_timeout
        com.dreamsoftware.coinscope.domain.util.NetworkError.TOO_MANY_REQUESTS -> R.string.error_too_many_requests
        com.dreamsoftware.coinscope.domain.util.NetworkError.NO_INTERNET -> R.string.error_no_internet
        com.dreamsoftware.coinscope.domain.util.NetworkError.SERVER_ERROR -> R.string.error_unknown
        com.dreamsoftware.coinscope.domain.util.NetworkError.SERIALIZATION -> R.string.error_serialization
        com.dreamsoftware.coinscope.domain.util.NetworkError.UNKNOWN -> R.string.error_unknown
    }
    return context.getString(resId)
}