package com.example.uzbexchangerate.fragments.history

import com.example.uzbexchangerate.models.ExchangeRate

data class HistoryUiEvent(
    val localData: List<ExchangeRate> = emptyList(),
)
