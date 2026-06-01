package com.naji.cashtracker.feature.dashboard.presentation.home

sealed interface HomeEvent {
    data object NavigateToAddTransaction : HomeEvent
    data object NavigateToCategories : HomeEvent
    data object NavigateToBudgets : HomeEvent
}
