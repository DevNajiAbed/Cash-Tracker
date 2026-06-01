package com.naji.cashtracker.feature.transaction.presentation.addedit

sealed interface AddTransactionAction {
    data class OnTypeChange(val type: TransactionType) : AddTransactionAction
    data class OnAmountChange(val amount: String) : AddTransactionAction
    data class OnCategorySelect(val categoryId: String) : AddTransactionAction
    data class OnNoteChange(val note: String) : AddTransactionAction
    data class OnDateSelected(val millis: Long?) : AddTransactionAction
    data object OnToggleDatePicker : AddTransactionAction
    data object OnSave : AddTransactionAction
    data object OnClose : AddTransactionAction
}
