package com.naji.cashtracker.feature.transaction.presentation.addedit

import com.naji.cashtracker.core.presentation.UiText

sealed interface AddTransactionEvent {
    data object NavigateBack : AddTransactionEvent
    data class ShowToast(val message: UiText) : AddTransactionEvent
}
