package com.example.uzbexchangerate.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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

}