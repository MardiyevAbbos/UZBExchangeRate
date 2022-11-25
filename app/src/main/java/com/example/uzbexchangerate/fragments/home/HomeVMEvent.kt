package com.example.uzbexchangerate.fragments.home

import com.example.uzbexchangerate.models.ExchangeRate

sealed class HomeVMEvent{
    object DoHold : HomeVMEvent()
    data class DoDateHold(val date: String) : HomeVMEvent()
    object GetLocalData : HomeVMEvent()
    data class SaveCurrencyToLocal(val currency: ExchangeRate) : HomeVMEvent()
    data class DeleteCurrencyToLocal(val ccy: String) : HomeVMEvent()
}
