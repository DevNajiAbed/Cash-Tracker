package com.naji.cashtracker.feature.auth.presentation.register

import com.naji.cashtracker.core.presentation.UiText

sealed interface RegisterEvent {
    data object NavigateToHome : RegisterEvent
    data class ShowToast(val message: UiText) : RegisterEvent
}
