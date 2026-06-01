package com.naji.cashtracker.feature.main.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.naji.cashtracker.feature.dashboard.presentation.home.HomeRoot
import com.naji.cashtracker.navigation.HomeRoute

fun NavGraphBuilder.mainGraph(
    navController: NavController,
    onNavigateToAddTransaction: () -> Unit,
    onNavigateToCategories: () -> Unit,
    onNavigateToBudgets: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    composable<HomeRoute> {
        MainScaffold(
            homeContent = {
                HomeRoot(
                    onNavigateToAddTransaction = onNavigateToAddTransaction,
                    onNavigateToCategories = onNavigateToCategories,
                    onNavigateToBudgets = onNavigateToBudgets
                )
            },
            transactionsContent = {
                PlaceholderScreen("Transactions")
            },
            analyticsContent = {
                PlaceholderScreen("Analytics")
            },
            settingsContent = {
                PlaceholderScreen("Settings")
            }
        )
    }
}

@Composable
private fun PlaceholderScreen(title: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
