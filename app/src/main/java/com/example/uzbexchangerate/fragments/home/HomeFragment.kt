package com.example.uzbexchangerate.fragments.home

import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import com.example.uzbexchangerate.R
import com.example.uzbexchangerate.adapter.CurrenciesAdapter
import com.example.uzbexchangerate.databinding.FragmentHomeBinding
import com.example.uzbexchangerate.dialogs.LoaderDialog
import com.example.uzbexchangerate.fragments.BaseFragment
import com.example.uzbexchangerate.models.CurrencyAndState
import com.example.uzbexchangerate.models.ExchangeRate
import com.example.uzbexchangerate.utils.Constants
import com.example.uzbexchangerate.utils.extensions.collectLA
import com.example.uzbexchangerate.utils.extensions.navigateSafe
import com.example.uzbexchangerate.utils.extensions.snackBar
import com.example.uzbexchangerate.utils.getListCurrencyAndState
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeVM: HomeVM by activityViewModels()
    private val currencyAdapter by lazy { CurrenciesAdapter(shared) }
    private val loader by lazy { LoaderDialog(requireContext()) }
    private var allCurrencies: ArrayList<ExchangeRate> = ArrayList()
    private var allCurrencyAndState: ArrayList<CurrencyAndState> = ArrayList()
    private var currentDate = ""

    override fun onViewCreate() {
        homeVM.onEvent(HomeVMEvent.DoHold)
        homeVM.onEvent(HomeVMEvent.GetLocalData)
        initViews()
        collectUiState()
    }

    private fun initViews() {
        val calendar: Calendar = Calendar.getInstance()
        currentDate = SimpleDateFormat("yyyy-MM-dd").format(calendar.time.time)
        binding.apply {
            rvCurrencies.adapter = currencyAdapter
            swipeRefresh.setOnRefreshListener {
                swipeRefresh.isRefreshing = false
                homeVM.onEvent(HomeVMEvent.DoHold)
            }
            ivSearch.setOnClickListener {
                val bundle = bundleOf(Constants.ALL_CURRENCY_AND_STATE_LIST to allCurrencyAndState)
                navController.navigateSafe(
                    R.id.action_homeFragment_to_searchFragment,
                    bundle
                )
            }
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
        currencyAdapter.setExchangeIconClickListener { currency ->
            val bundle2 = bundleOf(Constants.CURRENCY to currency)
            navController.navigateSafe(
                R.id.action_homeFragment_to_exchangeFragment,
                bundle2
            )
        }
    }

    private fun collectUiState() {
        binding.apply {
            homeVM.uiState.collectLA(viewLifecycleOwner) { state ->
                if (state.response != null) {
                    allCurrencies.clear()
                    allCurrencies.addAll(state.response)
                    currencyAdapter.setData(getListCurrencyAndState(state.response, emptyList()))
                }

                if (state.errorBody != null) {
                    snackBar(binding.root, state.errorBody.asString(requireContext()))
                }

                if (state.localData.isNotEmpty()){
                    if (currentDate.subSequence(8,10) != shared.currentDate.toString().subSequence(0,2)
                        || currentDate.subSequence(5,7) != shared.currentDate.toString().subSequence(3,5)){
                        allCurrencies.forEach { item ->
                           state.localData.forEach { local ->
                               if (item.Ccy == local.Ccy){
                                   //homeVM.onEvent(HomeVMEvent.UpdateCurrencyToLocal(item))
                                   homeVM.onEvent(HomeVMEvent.UpdateFieldsCurrencyToLocal(item.Rate, item.Diff, item.Date, item.Ccy))
                               }
                           }
                        }
                        shared.currentDate = currentDate
                    }
                    allCurrencyAndState.clear()
                    allCurrencyAndState.addAll(getListCurrencyAndState(allCurrencies, state.localData))
                    currencyAdapter.setData(getListCurrencyAndState(allCurrencies, state.localData))
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