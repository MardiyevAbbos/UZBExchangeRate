package com.example.uzbexchangerate.fragments.home

import com.example.uzbexchangerate.models.ExchangeRate
import com.example.uzbexchangerate.utils.UiText

data class HomeUiEvent(
    val isLoading: Boolean = false,
    val response: List<ExchangeRate>? = null,
    val localData: List<ExchangeRate> = emptyList(),
    val errorBody: UiText? = null
)
