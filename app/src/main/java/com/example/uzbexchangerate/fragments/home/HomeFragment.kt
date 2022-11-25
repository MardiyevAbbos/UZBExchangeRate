package com.example.uzbexchangerate.fragments.home

import androidx.fragment.app.activityViewModels
import com.example.uzbexchangerate.adapter.CurrenciesAdapter
import com.example.uzbexchangerate.databinding.FragmentHomeBinding
import com.example.uzbexchangerate.dialogs.DialogCalendar
import com.example.uzbexchangerate.dialogs.LoaderDialog
import com.example.uzbexchangerate.fragments.BaseFragment
import com.example.uzbexchangerate.utils.extensions.collectLA
import com.example.uzbexchangerate.utils.extensions.snackBar
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeVM: HomeVM by activityViewModels()
    private val currencyAdapter by lazy { CurrenciesAdapter() }
    private val loader by lazy { LoaderDialog(requireContext()) }

    override fun onViewCreate() {
        homeVM.onEvent(HomeVMEvent.DoHold)
        homeVM.onEvent(HomeVMEvent.GetLocalData)
        initViews()
        collectUiState()
    }

    private fun initViews() {
        val calendar: Calendar = Calendar.getInstance()
        val currentDate = SimpleDateFormat("yyyy-MM-dd").format(calendar.time.time)
        binding.tvDate.text = currentDate

        binding.llCalendar.setOnClickListener {
            val dialog = DialogCalendar(requireContext())

            dialog.setCanceledOnTouchOutside(true)
            dialog.show()
            dialog.setCancelable(true)
            dialog.setButtonClickListener {
                if (it != binding.tvDate.text) {
                    homeVM.onEvent(HomeVMEvent.DoDateHold(it))
                }
                binding.tvDate.text = it
                dialog.dismiss()
            }
        }
        binding.rvCurrencies.adapter = currencyAdapter
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            homeVM.onEvent(HomeVMEvent.DoDateHold(binding.tvDate.text.toString()))
        }
        currencyAdapter.setStarIconClickListener { currency, saveState ->
            if (saveState) {
                homeVM.onEvent(HomeVMEvent.SaveCurrencyToLocal(currency))
                homeVM.onEvent(HomeVMEvent.GetLocalData)
            } else {
                homeVM.onEvent(HomeVMEvent.DeleteCurrencyToLocal(currency.Ccy))
                homeVM.onEvent(HomeVMEvent.GetLocalData)
            }
        }
    }

    private fun collectUiState() {
        binding.apply {
            homeVM.uiState.collectLA(viewLifecycleOwner) { state ->
                if (state.response != null) {
                    currencyAdapter.setData(state.response)
                }

                if (state.errorBody != null) {
                    snackBar(binding.root, state.errorBody.asString(requireContext()))
                }

                if (state.localData.isNotEmpty()){
                    currencyAdapter.setLocalData(state.localData)
                }

                if (state.isLoading) {
                    loader.show()
                } else {
                    loader.dismiss()
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        homeVM.clearUiStateBeforeNav()
    }

}