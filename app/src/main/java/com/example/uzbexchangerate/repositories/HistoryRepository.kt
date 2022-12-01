package com.example.uzbexchangerate.repositories

import com.example.uzbexchangerate.room.dao.CurrencyDao
import javax.inject.Inject

class HistoryRepository @Inject constructor(
    private val currencyDao: CurrencyDao
) {

    suspend fun getAllCurrencyLocal() = currencyDao.getAllCurrency()

    suspend fun deleteCurrencyByIdLocal(ccy: String) = currencyDao.deleteCurrencyById(ccy)

}