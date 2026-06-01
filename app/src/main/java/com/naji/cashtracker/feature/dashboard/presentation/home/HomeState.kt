package com.naji.cashtracker.feature.dashboard.presentation.home

data class HomeState(
    val balance: String = "$0.00",
    val income: String = "$0",
    val expenses: String = "$0",
    val recentTransactions: List<TransactionUi> = emptyList(),
    val isLoading: Boolean = false
)

data class TransactionUi(
    val id: String,
    val icon: String,
    val iconColor: Long,
    val title: String,
    val subtitle: String,
    val amount: String,
    val isPositive: Boolean
)
