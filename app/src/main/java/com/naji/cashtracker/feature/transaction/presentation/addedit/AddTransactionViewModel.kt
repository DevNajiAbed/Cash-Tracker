package com.naji.cashtracker.feature.transaction.presentation.addedit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naji.cashtracker.R
import com.naji.cashtracker.core.presentation.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class AddTransactionViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(
        AddTransactionState(
            amount = savedStateHandle["amount"] ?: "",
            note = savedStateHandle["note"] ?: "",
            categories = sampleCategories(TransactionType.Income)
        )
    )
    val state = _state.asStateFlow()

    private val _events = Channel<AddTransactionEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: AddTransactionAction) {
        when (action) {
            is AddTransactionAction.OnTypeChange -> {
                _state.update {
                    it.copy(
                        transactionType = action.type,
                        selectedCategory = null,
                        categories = sampleCategories(action.type)
                    )
                }
            }
            is AddTransactionAction.OnAmountChange -> {
                savedStateHandle["amount"] = action.amount
                _state.update { it.copy(amount = action.amount) }
            }
            is AddTransactionAction.OnCategorySelect -> {
                _state.update { it.copy(selectedCategory = action.categoryId) }
            }
            is AddTransactionAction.OnNoteChange -> {
                savedStateHandle["note"] = action.note
                _state.update { it.copy(note = action.note) }
            }
            is AddTransactionAction.OnDateSelected -> {
                val formatted = formatDate(action.millis)
                _state.update {
                    it.copy(selectedDateMillis = action.millis, dateText = formatted, showDatePicker = false)
                }
            }
            AddTransactionAction.OnToggleDatePicker -> {
                _state.update { it.copy(showDatePicker = !it.showDatePicker) }
            }
            AddTransactionAction.OnSave -> saveTransaction()
            AddTransactionAction.OnClose -> {
                viewModelScope.launch { _events.send(AddTransactionEvent.NavigateBack) }
            }
        }
    }

    private fun saveTransaction() {
        val s = _state.value
        if (s.amount.isBlank() || s.amount == "$0.00" || s.selectedCategory == null) {
            viewModelScope.launch {
                _events.send(
                    AddTransactionEvent.ShowToast(
                        UiText.StringResource(R.string.add_tx_validation_error)
                    )
                )
            }
            return
        }
        viewModelScope.launch {
            _events.send(
                AddTransactionEvent.ShowToast(
                    UiText.StringResource(R.string.add_tx_saved_toast)
                )
            )
            _events.send(AddTransactionEvent.NavigateBack)
        }
        resetForm()
    }

    private fun resetForm() {
        savedStateHandle["amount"] = ""
        savedStateHandle["note"] = ""
        _state.update {
            it.copy(
                amount = "",
                note = "",
                selectedCategory = null,
                selectedDateMillis = null,
                dateText = "Today"
            )
        }
    }

    private fun formatDate(millis: Long?): String {
        if (millis == null) return "Today"
        val date = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()
        val today = LocalDate.now()
        return when (date) {
            today -> "Today"
            today.minusDays(1) -> "Yesterday"
            else -> date.format(DateTimeFormatter.ofPattern("MMMM d, yyyy"))
        }
    }
}
