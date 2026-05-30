package com.naji.cashtracker.feature.transaction.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.naji.cashtracker.navigation.AddTransactionRoute
import com.naji.cashtracker.navigation.EditTransactionRoute
import com.naji.cashtracker.navigation.TransactionsListRoute

fun NavGraphBuilder.transactionGraph(
    navController: NavController,
    onNavigateToAddEdit: (String?) -> Unit
) {
    navigation<TransactionsListRoute>(startDestination = TransactionsListRoute) {
        composable<TransactionsListRoute> {
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
