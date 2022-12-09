package com.example.uzbexchangerate.fragments.exchange

import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.activityViewModels
import com.dylanc.longan.safeArguments
import com.example.uzbexchangerate.BuildConfig
import com.example.uzbexchangerate.R
import com.example.uzbexchangerate.databinding.FragmentExchangeBinding
import com.example.uzbexchangerate.dialogs.CalendarDialog
import com.example.uzbexchangerate.dialogs.LoaderDialog
import com.example.uzbexchangerate.fragments.BaseFragment
import com.example.uzbexchangerate.models.ExchangeRate
import com.example.uzbexchangerate.utils.Constants.CURRENCY
import com.example.uzbexchangerate.utils.MultiplyAndDivideLargeNumberAsString
import com.example.uzbexchangerate.utils.extensions.*
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ExchangeFragment : BaseFragment<FragmentExchangeBinding>(FragmentExchangeBinding::inflate) {

    private val exchangeVM: ExchangeVM by activityViewModels()
    private val currency: ExchangeRate by safeArguments(CURRENCY)
    private var currency2: ExchangeRate? = null
    private val loader by lazy { LoaderDialog(requireContext()) }
    private var isFromUZS = false

    override fun onViewCreate() {
        currency2 = currency
        initViews()
        collectUiState()
    }

    private fun initViews() {
        binding.edtFromAmount.showKeyboard()
        setInfo(currency2!!)
        binding.ivExchange.setOnClickListener {
            isFromUZS = !isFromUZS
            setExchangeInfo(currency2!!)
        }
        setCalendarFunction()
        feelEdtAmountChanged()
    }

    private fun collectUiState() {
        exchangeVM.uiState.collectLA(viewLifecycleOwner) { uiState ->
            if (uiState.selectDateCurrency.isNotEmpty()) {
                currency2 = uiState.selectDateCurrency[0]
                setInfo(currency2!!)
            }
            if (uiState.errorBody != null) {
                snackBar(binding.root, uiState.errorBody.asString(requireContext()))
            }
            if (uiState.isLoading) {
                loader.show()
            } else {
                loader.dismiss()
            }
        }
    }

    private fun setCalendarFunction() {
        binding.tvDate.text = shared.currentDate
        binding.llCalendar.setOnClickListener {
            val dialog = CalendarDialog(requireContext())
            dialog.setCanceledOnTouchOutside(true)
            dialog.show()
            dialog.setCancelable(true)
            dialog.setButtonClickListener {
                if (it != binding.tvDate.text) {
                    exchangeVM.onEvent(ExchangeVMEvent.SelectedDateCurrency(currency2!!.Ccy, it))
                }
                binding.tvDate.text = it
                dialog.dismiss()
            }
        }
    }

    private fun feelEdtAmountChanged() {
        binding.edtFromAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.edtFromAmount.text.isNotEmpty()) {
                    val newAmount = convertCurrency(binding.edtFromAmount.text.toString())
                    binding.tvToAmount.text = newAmount
                } else {
                    binding.tvToAmount.text = ""
                }
                if (binding.edtFromAmount.text.toString().length == 24) binding.tvMaxLine.visible()
                else binding.tvMaxLine.invisible()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun convertCurrency(amount: String): String {
        var result: String = ""
        val rate = (currency2!!.Rate.toDouble() * 100).toInt()
        result = if (isFromUZS) {
            /*if ((amount.toInt() * 100) < rate){
                    result = (amount.toDouble() / currency2!!.Rate.toDouble()).toString()
                    return result.subSequence(0, 4).toString()
                }else{
                    result = MultiplyAndDivideLargeNumberAsString.longDivision(amount.plus("0000"), rate)
                }*/
            if (amount.length < currency2!!.Rate.length - 3) {
                return "0,00"
            }
            MultiplyAndDivideLargeNumberAsString.longDivision(amount.plus("0000"), rate)
        } else {
            MultiplyAndDivideLargeNumberAsString.longMultiply(amount, rate.toString())
        }
        var newResult = ""
        var counter = 0
        for (i in result.length - 1 downTo 0) {
            counter++
            newResult = if (counter == 2) {
                ",${result[i]}$newResult"
            } else if ((counter - 2) % 3 == 0 && (counter - 2) / 3 >= 1 && i != 0) {
                ".${result[i]}$newResult"
            } else {
                "${result[i]}$newResult"
            }
        }
        //return ("${result.subSequence(0, result.length-2)},${result.subSequence(result.length-2, result.length)}")
        return newResult
    }

    private fun setExchangeInfo(item: ExchangeRate) {
        if (binding.edtFromAmount.text.toString().isNotEmpty()) {
            binding.tvToAmount.text = convertCurrency(binding.edtFromAmount.text.toString())
        }
        binding.apply {
            if (isFromUZS) {
                tvFromName.text = getString(R.string.str_uzbek_soum)
                tvFromCcy.text = getString(R.string.str_uzs)
                tvToName.text = item.CcyNm_EN
                tvToCcy.text = item.Ccy
            } else {
                tvFromName.text = item.CcyNm_EN
                tvFromCcy.text = item.Ccy
                tvToName.text = getString(R.string.str_uzbek_soum)
                tvToCcy.text = getString(R.string.str_uzs)
            }
        }
    }

    private fun setInfo(item: ExchangeRate) {
        isFromUZS = false
        binding.apply {
            tvFromName.text = item.CcyNm_EN
            edtFromAmount.setText(getString(R.string.str_one_amount))
            tvFromCcy.text = item.Ccy
            tvToName.text = getString(R.string.str_uzbek_soum)
            tvToCcy.text = getString(R.string.str_uzs)
            tvToAmount.text = item.Rate
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        exchangeVM.clearUiStateBeforeNav()
    }

}
