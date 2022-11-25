package com.example.uzbexchangerate.fragments.history

import com.example.uzbexchangerate.models.ExchangeRate
import com.example.uzbexchangerate.utils.UiText

data class HistoryUiState(
    val localData: List<ExchangeRate> = emptyList(),
)
