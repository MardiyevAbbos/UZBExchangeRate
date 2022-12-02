package com.example.uzbexchangerate.fragments.search

import android.text.Editable
import android.text.TextWatcher
import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import com.example.uzbexchangerate.R
import com.example.uzbexchangerate.adapter.SearchAdapter
import com.example.uzbexchangerate.databinding.FragmentSearchBinding
import com.example.uzbexchangerate.fragments.BaseFragment
import com.example.uzbexchangerate.models.CurrencyAndState
import com.example.uzbexchangerate.models.ExchangeRate
import com.example.uzbexchangerate.utils.Constants
import com.example.uzbexchangerate.utils.extensions.navigateSafe
import com.example.uzbexchangerate.utils.extensions.showKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val currencyList: List<CurrencyAndState>
        get() = arguments?.get(Constants.ALL_CURRENCY_AND_STATE_LIST) as List<CurrencyAndState>

    private val searchAdapter by lazy { SearchAdapter() }

    override fun onViewCreate() {
        searchAdapter.setData(currencyList)
        binding.rvListRecipients.adapter = searchAdapter
        initViews()
    }

    private fun initViews() {
        binding.searchText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchAdapter.filter.filter(s)
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.searchText.showKeyboard()
        binding.clearIcon.setOnClickListener {
            binding.searchText.setText("")
        }

        searchAdapter.setItemClickListener { currency ->
            val bundle = bundleOf(Constants.CURRENCY to currency)
            navController.navigate(
                R.id.action_searchFragment_to_exchangeFragment,
                bundle,
                NavOptions.Builder().setPopUpTo(R.id.searchFragment, true).build()
            )
        }
    }

}