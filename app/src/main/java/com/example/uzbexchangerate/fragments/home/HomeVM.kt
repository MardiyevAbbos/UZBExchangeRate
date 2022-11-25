package com.example.uzbexchangerate.fragments.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uzbexchangerate.repositories.MainRepository
import com.example.uzbexchangerate.utils.NetworkResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel(){

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: HomeVMEvent){
        when(event){
            is HomeVMEvent.DoHold ->{
                viewModelScope.launch {
                    _uiState.update { it.copy(isLoading = true) }
                    when(val result = mainRepository.getAllCurrency()){
                        is NetworkResource.Success ->{
                            _uiState.update { it.copy(response = result.data, isLoading = false, errorBody = null) }
                        }
                        is NetworkResource.Error ->{
                            _uiState.update { it.copy(errorBody = result.uiText, isLoading = false) }
                        }
                    }
                }
            }
            is HomeVMEvent.DoDateHold ->{
                viewModelScope.launch {
                    _uiState.update { it.copy(isLoading = true) }
                    when(val result = mainRepository.getDateAllCurrency(event.date)){
                        is NetworkResource.Success -> {
                            _uiState.update { it.copy(response = result.data, isLoading = false, errorBody = null) }
                        }
                        is NetworkResource.Error -> {
                            _uiState.update { it.copy(errorBody = result.uiText, isLoading = false) }
                        }
                    }
                }
            }
            is HomeVMEvent.GetLocalData -> {
                viewModelScope.launch {
                    val localData = mainRepository.getAllCurrencyLocal()
                    _uiState.update { it.copy(localData = localData) }
                }
            }
            is HomeVMEvent.SaveCurrencyToLocal -> {
                viewModelScope.launch {
                    mainRepository.saveCurrencyLocal(event.currency)
                }
            }
            is HomeVMEvent.DeleteCurrencyToLocal -> {
                viewModelScope.launch {
                    mainRepository.deleteCurrencyByIdLocal(event.ccy)
                }
            }
        }
        Log.d("TAG@@@ info", "onEvent: $event")
    }

    fun clearUiStateBeforeNav() {
        _uiState.update { it.copy(response = null, localData = emptyList(), isLoading = false, errorBody = null) }
    }

}