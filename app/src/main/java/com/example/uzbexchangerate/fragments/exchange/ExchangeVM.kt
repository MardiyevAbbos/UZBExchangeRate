package com.example.uzbexchangerate.fragments.exchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uzbexchangerate.repositories.ExchangeRepository
import com.example.uzbexchangerate.utils.NetworkResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangeVM @Inject constructor(
    private val exchangeRepository: ExchangeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExchangeUiEvent())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: ExchangeVMEvent){
        when(event){
            is ExchangeVMEvent.SelectedDateCurrency -> {
                viewModelScope.launch {
                    _uiState.update { it.copy(isLoading = true) }
                    when(val result = exchangeRepository.getSelectDateCurrency(event.ccy, event.date)){
                        is NetworkResource.Success ->{
                            _uiState.update { it.copy(selectDateCurrency = result.data!!, isLoading = false) }
                        }
                        is NetworkResource.Error ->{
                            _uiState.update { it.copy(errorBody = result.uiText, isLoading = false) }
                        }
                    }
                }
            }
        }
    }

    fun clearUiStateBeforeNav() {
        _uiState.update { it.copy(selectDateCurrency = emptyList(), isLoading = false) }
    }

}