package com.example.uzbexchangerate.fragments.history

import androidx.fragment.app.activityViewModels
import com.example.uzbexchangerate.adapter.CurrenciesAdapter
import com.example.uzbexchangerate.databinding.FragmentHistoryBinding
import com.example.uzbexchangerate.fragments.BaseFragment
import com.example.uzbexchangerate.utils.extensions.collectLA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>(FragmentHistoryBinding::inflate) {

    private val historyVM: HistoryVM by activityViewModels()
    private val currencyAdapter by lazy { CurrenciesAdapter() }

    override fun onViewCreate() {
        historyVM.onEvent(HistoryVMEvent.GetLocalData)
        initViews()
        collectUiState()
    }

    private fun initViews() {
        binding.rvCurrencies.adapter = currencyAdapter
    }

    private fun collectUiState() {
        historyVM.uiState.collectLA(viewLifecycleOwner){ uiState ->
            if (uiState.localData.isNotEmpty()){
                currencyAdapter.setData(uiState.localData)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        historyVM.clearUiStateBeforeNav()
    }

}