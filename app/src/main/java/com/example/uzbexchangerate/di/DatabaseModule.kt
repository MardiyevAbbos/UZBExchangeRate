package com.example.uzbexchangerate.di

import android.content.Context
import androidx.room.Room
import com.example.uzbexchangerate.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun getDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "appData.db").build()

    @Provides
    @Singleton
    fun getCurrencyDao(database: AppDatabase) = database.getCurrencyDao()

}