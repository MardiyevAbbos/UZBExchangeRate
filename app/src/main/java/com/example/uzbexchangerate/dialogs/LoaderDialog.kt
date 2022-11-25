package com.example.uzbexchangerate.dialogs

import androidx.appcompat.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.uzbexchangerate.databinding.DialogLoaderBinding

class LoaderDialog(context: Context) : AlertDialog(context) {
    val binding = DialogLoaderBinding.inflate(LayoutInflater.from(context))

    init {
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        setCancelable(false)
        setView(binding.root)
    }

}