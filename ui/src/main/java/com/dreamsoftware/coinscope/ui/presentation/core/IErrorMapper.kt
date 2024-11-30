package com.dreamsoftware.coinscope.ui.presentation.core

interface IErrorMapper {
    fun mapToMessage(ex: Throwable): String
}