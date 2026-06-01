package com.naji.cashtracker.feature.budget.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.naji.cashtracker.navigation.AddBudgetRoute
import com.naji.cashtracker.navigation.BudgetListRoute
import com.naji.cashtracker.navigation.BudgetsRoute
import com.naji.cashtracker.navigation.EditBudgetRoute

fun NavGraphBuilder.budgetGraph(
    navController: NavController,
    onNavigateToAddBudget: () -> Unit
) {
    navigation<BudgetsRoute>(startDestination = BudgetListRoute) {
        composable<BudgetListRoute> {
            Text("Budgets")
        }
        composable<AddBudgetRoute> {
            Text("Add Budget")
        }
        composable<EditBudgetRoute> {
            Text("Edit Budget")
        }
    }
}
