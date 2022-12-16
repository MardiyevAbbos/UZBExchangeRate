package com.example.uzbexchangerate.utils.extensions

import android.view.View
import android.view.ViewTreeObserver
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar


fun snackBar(view: View?, message: String) {
    view?.let {
        Snackbar.make(
            view,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}

