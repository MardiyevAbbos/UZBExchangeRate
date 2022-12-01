package com.example.uzbexchangerate.utils.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager


fun View.gone(): View{
    visibility = View.GONE
    return this
}

fun View.visible(): View{
    visibility = View.VISIBLE
    return this
}

fun View.invisible(): View{
    visibility = View.INVISIBLE
    return this
}

fun View.showKeyboard(delayMillis: Long = 500) {
    this.requestFocus()
    if (isFocused) {
        postDelayed({
            // We still post the call, just in case we are being notified of the windows focus
            // but InputMethodManager didn't get properly setup yet.
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
        }, delayMillis)
    }
}