package com.example.uzbexchangerate.fragments.exchange

import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.activityViewModels
import com.dylanc.longan.safeArguments
import com.example.uzbexchangerate.R
import com.example.uzbexchangerate.databinding.FragmentExchangeBinding
import com.example.uzbexchangerate.dialogs.CalendarDialog
import com.example.uzbexchangerate.dialogs.LoaderDialog
import com.example.uzbexchangerate.fragments.BaseFragment
import com.example.uzbexchangerate.models.ExchangeRate
import com.example.uzbexchangerate.utils.Constants.CURRENCY
import com.example.uzbexchangerate.utils.extensions.collectLA
import com.example.uzbexchangerate.utils.extensions.showKeyboard
import com.example.uzbexchangerate.utils.extensions.snackBar
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
        binding.apply {
            tvFromName.text = currency2!!.CcyNm_EN
            edtFromAmount.setText(getString(R.string.str_one_amount))
            tvFromCcy.text = currency2!!.Ccy
            tvToName.text = getString(R.string.str_uzbek_soum)
            tvToCcy.text = getString(R.string.str_uzs)
            tvToAmount.text = currency2!!.Rate
        }
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
                setExchangeInfo(uiState.selectDateCurrency[0])
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
        val calendar: Calendar = Calendar.getInstance()
        val currentDate = SimpleDateFormat("yyyy-MM-dd").format(calendar.time.time)
        binding.tvDate.text = currentDate
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
                    val newAmount = if (isFromUZS) (binding.edtFromAmount.text.toString().toFloat() / currency2!!.Rate.toFloat())
                    else (binding.edtFromAmount.text.toString().toFloat() * currency2!!.Rate.toFloat())
                    binding.tvToAmount.text = newAmount.toString()
                }else{
                    binding.tvToAmount.text = "0"
                }

            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setExchangeInfo(item: ExchangeRate) {
        binding.apply {
            if (isFromUZS) {
                tvFromName.text = getString(R.string.str_uzbek_soum)
                edtFromAmount.setText(item.Rate)
                tvFromCcy.text = getString(R.string.str_uzs)
                tvToName.text = item.CcyNm_EN
                tvToAmount.text = getString(R.string.str_one_amount)
                tvToCcy.text = item.Ccy
            } else {
                tvFromName.text = item.CcyNm_EN
                edtFromAmount.setText(getString(R.string.str_one_amount))
                tvFromCcy.text = item.Ccy
                tvToName.text = getString(R.string.str_uzbek_soum)
                tvToCcy.text = getString(R.string.str_uzs)
                tvToAmount.text = item.Rate
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        exchangeVM.clearUiStateBeforeNav()
    }

}
