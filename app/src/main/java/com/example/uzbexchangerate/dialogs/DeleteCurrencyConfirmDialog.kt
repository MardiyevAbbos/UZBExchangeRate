package com.example.uzbexchangerate.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import com.example.uzbexchangerate.databinding.DialogDeleteConfirmBinding

class DeleteCurrencyConfirmDialog(context: Context, infoTitle: String) : AlertDialog(context) {

    private val binding = DialogDeleteConfirmBinding.inflate(LayoutInflater.from(context))
    private var yesButtonClickListener: (() -> Unit)? = null

    fun setYesButtonClickListener(f: () -> Unit) {
        yesButtonClickListener = f
    }

    init {
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.setGravity(Gravity.BOTTOM)
        setCancelable(false)
        binding.txtDialog.text = infoTitle
        binding.btnNo.setOnClickListener {
            dismiss()
        }
        binding.btnYes.setOnClickListener {
            yesButtonClickListener?.invoke()
            dismiss()
        }
        setView(binding.root)
    }

}