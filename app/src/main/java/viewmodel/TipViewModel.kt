package viewmodel

import model.Tip
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TipViewModel: ViewModel() {

    private val _state = MutableStateFlow(Tip())
    val uiState = _state.asStateFlow()

    fun updateAmount(amount: Double){
        _state.value = _state.value.copy(ammount = amount)
        calculateAll()
    }

    fun updateCustom(custom: Int){
        _state.value = _state.value.copy(custom = custom)
        calculateAll()
    }

    private fun calculateAll() {
        val current = _state.value
        val tip15 = current.ammount * 0.15
        val total15 = current.ammount + tip15
        val tipCustom = current.ammount * (current.custom / 100.0)
        val totalCustom = current.ammount + tipCustom
        
        _state.value = current.copy(
            tip15 = tip15,
            total15 = total15,
            tipCustom = tipCustom,
            totalCustom = totalCustom
        )
    }
}
