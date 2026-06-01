package com.naji.cashtracker.feature.dashboard.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _events = Channel<HomeEvent>()
    val events = _events.receiveAsFlow()

    init {
        loadSampleData()
    }

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.OnRefresh -> loadSampleData()
            HomeAction.OnAddTransaction -> {
                viewModelScope.launch { _events.send(HomeEvent.NavigateToAddTransaction) }
            }
            HomeAction.OnCategoriesClick -> {
                viewModelScope.launch { _events.send(HomeEvent.NavigateToCategories) }
            }
            HomeAction.OnBudgetsClick -> {
                viewModelScope.launch { _events.send(HomeEvent.NavigateToBudgets) }
            }
        }
    }

    private fun loadSampleData() {
        _state.update { it.copy(isLoading = true) }
        _state.update {
            it.copy(
                balance = "$12,430.00",
                income = "+$5,240",
                expenses = "-$3,180",
                recentTransactions = listOf(
                    TransactionUi(
                        id = "1",
                        icon = "F",
                        iconColor = 0xFF10B981,
                        title = "Food",
                        subtitle = "Today, 2:30 PM",
                        amount = "-$45",
                        isPositive = false
                    ),
                    TransactionUi(
                        id = "2",
                        icon = "S",
                        iconColor = 0xFF4F46E5,
                        title = "Salary",
                        subtitle = "Today, 9:00 AM",
                        amount = "+$3,500",
                        isPositive = true
                    ),
                    TransactionUi(
                        id = "3",
                        icon = "T",
                        iconColor = 0xFFF59E0B,
                        title = "Transport",
                        subtitle = "Yesterday",
                        amount = "-$12",
                        isPositive = false
                    )
                ),
                isLoading = false
            )
        }
    }
}
