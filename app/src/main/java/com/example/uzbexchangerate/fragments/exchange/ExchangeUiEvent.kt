package com.example.uzbexchangerate.fragments.exchange

import com.example.uzbexchangerate.models.ExchangeRate
import com.example.uzbexchangerate.utils.UiText

data class ExchangeUiEvent(
    val isLoading: Boolean = false,
    val selectDateCurrency: List<ExchangeRate> = emptyList(),
    val errorBody: UiText? = null
)
