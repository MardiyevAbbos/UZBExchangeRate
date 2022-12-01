package com.example.uzbexchangerate.models

data class CurrencyAndState(
    val currency: ExchangeRate,
    val state: Boolean = false
) : java.io.Serializable