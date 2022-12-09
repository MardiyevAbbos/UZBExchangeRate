package com.example.uzbexchangerate.dialogs

import android.content.Context
import android.view.ViewGroup
import com.example.uzbexchangerate.R
import com.example.uzbexchangerate.databinding.DialogChangeLanguageBinding
import com.example.uzbexchangerate.utils.extensions.setRightDrawable
import com.example.uzbexchangerate.utils.extensions.snackBar
import com.google.android.material.bottomsheet.BottomSheetDialog

class ChangeLanguageDialog(context: Context, var chosenLanguage: String) :
    BottomSheetDialog(context) {

    val binding = DialogChangeLanguageBinding.inflate(
        layoutInflater,
        (layoutInflater.inflate(R.layout.dialog_change_language, null) as ViewGroup),
        false
    )

    private var onLanguageChoose: ((String) -> Unit)? = null
    fun setButtonClickListener(f: (language: String) -> Unit) {
        onLanguageChoose = f
    }

    init {
        window!!.setBackgroundDrawableResource(R.color.transparent)

        binding.apply {
            changeSelectedLanguage(
                chosenLanguage == "uz",
                chosenLanguage == "en",
                chosenLanguage == "ru"
            )
            exitBtn.setOnClickListener { dismiss() }
            englishBtn.setOnClickListener{
                changeSelectedLanguage(isEng = true)
                chosenLanguage = "en"
            }
            uzbBtn.setOnClickListener {
                changeSelectedLanguage(isUzb = true)
                chosenLanguage = "uz"
            }
            russianBtn.setOnClickListener {
                changeSelectedLanguage(isRus = true)
                chosenLanguage = "ru"
            }
            applyBtn.setOnClickListener {
                onLanguageChoose?.invoke(chosenLanguage)
                snackBar(window?.decorView!!, "Til tanlangan")
            }
        }
        setContentView(binding.root)
    }

    private fun changeSelectedLanguage(
        isUzb: Boolean = false,
        isEng: Boolean = false,
        isRus: Boolean = false
    ) {
        binding.apply {
            uzbBtn.setRightDrawable(if (isUzb) R.drawable.checkbox_circle_selected else R.drawable.checkbox_circle_not_selected)
            englishBtn.setRightDrawable(if (isEng) R.drawable.checkbox_circle_selected else R.drawable.checkbox_circle_not_selected)
            russianBtn.setRightDrawable(if (isRus) R.drawable.checkbox_circle_selected else R.drawable.checkbox_circle_not_selected)
        }
    }

}