package com.example.uzbexchangerate.fragments.exchange


sealed class ExchangeVMEvent {
    data class SelectedDateCurrency(
        val ccy: String,
        val date: String
    ) : ExchangeVMEvent()
}
