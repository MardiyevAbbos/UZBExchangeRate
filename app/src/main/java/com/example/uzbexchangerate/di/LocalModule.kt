package com.example.uzbexchangerate.di

import android.content.Context
import com.example.uzbexchangerate.utils.SharedPreferencesHelper
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @Provides
    @Singleton
    fun getSharedPreferences(@ApplicationContext context: Context): SharedPreferencesHelper = SharedPreferencesHelper(context = context)

    @Provides
    @Singleton
    fun provideGson() = Gson()

}