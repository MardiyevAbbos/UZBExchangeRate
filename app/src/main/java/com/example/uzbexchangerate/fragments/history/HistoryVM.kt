package com.example.uzbexchangerate.fragments.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uzbexchangerate.repositories.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryVM @Inject constructor(
    private val historyRepository: HistoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryUiEvent())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: HistoryVMEvent){
        when(event){
            is HistoryVMEvent.GetLocalData -> {
                viewModelScope.launch {
                    val result = historyRepository.getAllCurrencyLocal()
                    _uiState.update { it.copy(localData = result) }
                }
            }
            is HistoryVMEvent.DeleteCurrencyToLocal -> {
                viewModelScope.launch {
                    historyRepository.deleteCurrencyByIdLocal(event.ccy)
                }
            }
        }
    }

    fun clearUiStateBeforeNav() {
        _uiState.update { it.copy(localData = emptyList()) }
    }

}