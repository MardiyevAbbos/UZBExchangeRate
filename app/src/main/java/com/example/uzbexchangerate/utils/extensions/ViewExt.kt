package com.example.uzbexchangerate.utils.extensions

import android.view.View


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