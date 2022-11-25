package com.example.uzbexchangerate.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.uzbexchangerate.models.ExchangeRate
import com.example.uzbexchangerate.room.dao.CurrencyDao

@Database(entities = [ExchangeRate::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getCurrencyDao(): CurrencyDao
}