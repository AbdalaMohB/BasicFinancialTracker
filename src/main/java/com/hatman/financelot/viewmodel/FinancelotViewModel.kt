package com.hatman.financelot.viewmodel
import com.hatman.financelot.models.FinalResult
import com.hatman.financelot.models.FinancialState
import androidx.lifecycle.ViewModel
import com.hatman.financelot.models.SingleExpense
import com.hatman.financelot.models.SingleGain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FinancelotViewModel: ViewModel() {
    private val _uiState=MutableStateFlow(FinalResult())
    val uiState: StateFlow<FinalResult> = _uiState.asStateFlow()
    //TODO: ADD A "BY" variable
    fun getSinglesCost(): Double{
        return uiState.value.exSingle.sumOf { it.cost.toDouble() }
    }
    fun getSinglesGain(): Double{
        return uiState.value.pSingle.sumOf { it.gain.toDouble() }
    }
    fun addCost(newExp: SingleExpense){
        val costs=uiState.value.exSingle.toMutableList()
        costs.add(newExp)
        _uiState.update { it.copy(exSingle = costs) }
        breaksEven()
    }
    fun addGain(newGain: SingleGain){
        val gains=uiState.value.pSingle.toMutableList()
        gains.add(newGain)
        _uiState.update { it.copy(pSingle = gains) }
        breaksEven()
    }
    fun breaksEven(){
        val profit=getSinglesGain()-getSinglesCost()
        val fState=when {
            profit<0 -> FinancialState.LOSS
            profit>0 -> FinancialState.PROFIT
            else -> FinancialState.EVEN
        }
        _uiState.update { it.copy(state = fState, value = profit) }
    }
}