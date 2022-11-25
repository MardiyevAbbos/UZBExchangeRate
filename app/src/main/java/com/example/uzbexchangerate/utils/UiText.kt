package com.example.uzbexchangerate.utils

import android.content.Context
import androidx.annotation.StringRes
import com.example.uzbexchangerate.R
import java.io.IOException

sealed class UiText {
    data class DynamicString(@StringRes val resId: Int) : UiText()
    data class StaticString(val e: Exception? = null) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> context.getString(resId)
            is StaticString -> {
                if (e is IOException) {
                    context.getString(R.string.check_your_connection)
                } else {
                    context.getString(R.string.something_is_wrong)
                }
            }
        }
    }
}
