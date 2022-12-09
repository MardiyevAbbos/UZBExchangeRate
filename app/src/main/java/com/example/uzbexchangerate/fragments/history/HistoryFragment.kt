package com.example.uzbexchangerate.fragments.history

import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.uzbexchangerate.R
import com.example.uzbexchangerate.adapter.HistoryAdapter
import com.example.uzbexchangerate.databinding.FragmentHistoryBinding
import com.example.uzbexchangerate.dialogs.DeleteCurrencyConfirmDialog
import com.example.uzbexchangerate.fragments.BaseFragment
import com.example.uzbexchangerate.utils.Constants
import com.example.uzbexchangerate.utils.extensions.collectLA
import com.example.uzbexchangerate.utils.extensions.navigateSafe
import com.example.uzbexchangerate.utils.mylibrary.RecyclerItemTouchHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>(FragmentHistoryBinding::inflate) {

    private val historyVM: HistoryVM by activityViewModels()
    private val currencyAdapter by lazy { HistoryAdapter(shared) }
    private val deleteDialog by lazy { DeleteCurrencyConfirmDialog(requireContext(), getString(R.string.str_do_you_want_to____)) }

    override fun onViewCreate() {
        historyVM.onEvent(HistoryVMEvent.GetLocalData)
        initViews()
        collectUiState()
    }

    private fun initViews() {
        binding.rvCurrencies.adapter = currencyAdapter
        val helper = ItemTouchHelper(RecyclerItemTouchHelper(requireContext(), 100f).itemTouchHelper)
        helper.apply { attachToRecyclerView(binding.rvCurrencies) }

        currencyAdapter.setDeleteIconClickListener { ccy ->
            deleteDialog.show()
            deleteDialog.setYesButtonClickListener {
                historyVM.onEvent(HistoryVMEvent.DeleteCurrencyToLocal(ccy = ccy))
                historyVM.onEvent(HistoryVMEvent.GetLocalData)
                historyVM.onEvent(HistoryVMEvent.GetLocalData)
            }
        }
        currencyAdapter.setExchangeIconClickListener { currency ->
            val bundle = bundleOf(Constants.CURRENCY to currency)
            navController.navigateSafe(
                R.id.action_historyFragment_to_exchangeFragment,
                bundle
            )
        }
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