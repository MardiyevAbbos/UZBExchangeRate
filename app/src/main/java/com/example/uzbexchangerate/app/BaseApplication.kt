package com.example.uzbexchangerate.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application(){

    override fun onCreate() {
        super.onCreate()

    }

}