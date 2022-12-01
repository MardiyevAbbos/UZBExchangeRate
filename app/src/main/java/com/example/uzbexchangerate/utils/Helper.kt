package com.example.uzbexchangerate.utils

import com.example.uzbexchangerate.models.CurrencyAndState
import com.example.uzbexchangerate.models.ExchangeRate

fun getListCurrencyAndState(
    currencies: List<ExchangeRate>,
    localData: List<ExchangeRate> = emptyList()
): List<CurrencyAndState> {
    val result: ArrayList<CurrencyAndState> = ArrayList()
    val ccy: ArrayList<String> = ArrayList()

    localData.forEach { item ->
        ccy.add(item.Ccy)
    }
    currencies.forEach { item ->
        result.add(
            CurrencyAndState(
                item,
                ccy.contains(item.Ccy)
            )
        )
    }
    return result
}