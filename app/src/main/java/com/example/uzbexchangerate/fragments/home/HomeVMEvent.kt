package com.example.uzbexchangerate.fragments.home

import com.example.uzbexchangerate.models.ExchangeRate

sealed class HomeVMEvent {
    object DoHold : HomeVMEvent()
    data class DoDateHold(val date: String) : HomeVMEvent()
    object GetLocalData : HomeVMEvent()
    data class SaveCurrencyToLocal(val currency: ExchangeRate) : HomeVMEvent()
    data class DeleteCurrencyToLocal(val ccy: String) : HomeVMEvent()
    data class UpdateCurrencyToLocal(val currency: ExchangeRate) : HomeVMEvent()
    data class UpdateFieldsCurrencyToLocal(
        val rate: String,
        val diff: String,
        val date: String,
        val ccy: String
    ) : HomeVMEvent()
}
