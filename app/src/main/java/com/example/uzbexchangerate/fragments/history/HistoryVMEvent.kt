package com.example.uzbexchangerate.fragments.history


sealed class HistoryVMEvent{
    object GetLocalData : HistoryVMEvent()
    data class DeleteCurrencyToLocal(val ccy: String) : HistoryVMEvent()
}
