package com.naji.cashtracker.feature.dashboard.presentation.home

sealed interface HomeAction {
    data object OnRefresh : HomeAction
    data object OnAddTransaction : HomeAction
    data object OnCategoriesClick : HomeAction
    data object OnBudgetsClick : HomeAction
}
