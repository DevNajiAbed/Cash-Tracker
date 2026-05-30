package com.naji.cashtracker.feature.dashboard.presentation

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.naji.cashtracker.navigation.HomeRoute

fun NavGraphBuilder.dashboardGraph(
    navController: NavController,
    onNavigateToAddTransaction: () -> Unit,
    onNavigateToCategories: () -> Unit,
    onNavigateToBudgets: () -> Unit
) {
    navigation<HomeRoute>(startDestination = HomeRoute) {
        composable<HomeRoute> {
            Text("Dashboard")
        }
    }
}
