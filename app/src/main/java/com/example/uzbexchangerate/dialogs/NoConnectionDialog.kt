package com.example.uzbexchangerate.dialogs

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.uzbexchangerate.databinding.DialogNoConnectionBinding

class NoConnectionDialog(context: Context) : AlertDialog(context) {
    private val binding = DialogNoConnectionBinding.inflate(LayoutInflater.from(context))

    init {
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setGravity(Gravity.BOTTOM)

        binding.checkConnection.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                try {
                    context.startActivity(Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY))
                } catch (e: Exception) {
                    //logcat { e.asLog() }
                }
            } else {
                try {
                    context.startActivity(Intent(Settings.ACTION_DATA_ROAMING_SETTINGS))
                } catch (e: Exception) {
                    //logcat { e.asLog() }
                }
            }
        }
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        setView(binding.root)
    }
}