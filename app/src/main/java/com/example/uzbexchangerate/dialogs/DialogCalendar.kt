package com.example.uzbexchangerate.dialogs

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import com.example.uzbexchangerate.R
import com.example.uzbexchangerate.databinding.DialogCalendarBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.*

class DialogCalendar(context: Context) : BottomSheetDialog(context) {
    private var buttonClickListener: ((String) -> Unit)? = null

    fun setButtonClickListener(f: (date: String) -> Unit) {
        buttonClickListener = f
    }

    private val binding = DialogCalendarBinding.inflate(
        layoutInflater, (layoutInflater.inflate(R.layout.dialog_calendar, null) as ViewGroup), false
    )

    init {
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = sdf.format(Date())
        var data = currentDate

        binding.apply {
            val calendar: Calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, 0)
            calendarView.maxDate = calendar.time.time
            calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
                data = if (month + 1 < 10) {
                    if (dayOfMonth < 10) {
                        "$year-0${month + 1}-0$dayOfMonth"
                    } else {
                        "$year-0${month + 1}-$dayOfMonth"
                    }
                } else {
                    if (dayOfMonth < 10) {
                        "$year-${month + 1}-0$dayOfMonth"
                    } else {
                        "$year-${month + 1}-$dayOfMonth"
                    }
                }
            }
            continueBtn.setOnClickListener {
                buttonClickListener?.invoke(data)
            }
        }

        setContentView(binding.root)
    }
}