package com.example.uzbexchangerate.utils

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.uzbexchangerate.utils.Constants.APP_PREFS_NAME
import com.example.uzbexchangerate.utils.Constants.CURRENT_DATE
import com.example.uzbexchangerate.utils.Constants.PREF_APP_THEME_MODE
import com.example.uzbexchangerate.utils.Constants.PREF_SYSTEM_MODE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Mardiyev Abbos on 02.12.2022
 */
@Singleton
class SharedPreferencesHelper @Inject constructor(
    @ApplicationContext context: Context
) {

    private val masterKey by lazy {
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
    }

    val preferences by lazy {
        EncryptedSharedPreferences.create(
            context,
            APP_PREFS_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun setTheme(order: String) {
        preferences.edit {
            putString(PREF_APP_THEME_MODE, order)
        }
    }

    fun getTheme() = preferences.getString(PREF_APP_THEME_MODE, ThemeModeState.SYSTEM.name)

    var systemMode
        get() = preferences.getBoolean(PREF_SYSTEM_MODE, true)
        set(value) = preferences.edit { putBoolean(PREF_SYSTEM_MODE, value) }

    var currentDate
        get() = preferences.getString(CURRENT_DATE, "")
        set(value) = preferences.edit { putString(CURRENT_DATE, value) }


}