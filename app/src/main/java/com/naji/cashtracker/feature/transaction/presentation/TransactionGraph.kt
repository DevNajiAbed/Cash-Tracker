package com.naji.cashtracker.feature.transaction.presentation

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.naji.cashtracker.navigation.AddTransactionRoute
import com.naji.cashtracker.navigation.EditTransactionRoute
import com.naji.cashtracker.navigation.TransactionListRoute
import com.naji.cashtracker.navigation.TransactionsRoute

fun NavGraphBuilder.transactionGraph(
    navController: NavController,
    onNavigateToAddEdit: (String?) -> Unit
) {
    navigation<TransactionsRoute>(startDestination = TransactionListRoute) {
        composable<TransactionListRoute> {
            Text("Transactions List")
        }
        composable<AddTransactionRoute> {
            Text("Add Transaction")
        }
        composable<EditTransactionRoute> {
            Text("Edit Transaction")
        }
    }
}
