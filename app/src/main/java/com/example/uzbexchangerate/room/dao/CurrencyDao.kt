package com.example.uzbexchangerate.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.uzbexchangerate.models.ExchangeRate

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveCurrency(currencyEntity: ExchangeRate)

    @Query("SELECT * FROM currencies")
    suspend fun getAllCurrency(): List<ExchangeRate>

    @Query("SELECT * FROM currencies WHERE Ccy = :ccy")
    suspend fun getCurrencyById(ccy: String): ExchangeRate

    @Query("DELETE FROM currencies WHERE Ccy = :ccy")
    suspend fun deleteCurrencyById(ccy: String)

    @Query("DELETE FROM currencies")
    suspend fun deleteAllCurrency()

    @Update
    suspend fun updateCurrency(exchangeRate: ExchangeRate)

    @Query("UPDATE currencies SET Rate = :rate, Diff = :diff, Date = :date WHERE Ccy = :ccy")
    suspend fun updateCurrencyFields(rate: String, diff: String, date: String, ccy: String)
}