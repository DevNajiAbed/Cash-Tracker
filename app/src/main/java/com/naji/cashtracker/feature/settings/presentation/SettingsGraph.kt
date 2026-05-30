package com.naji.cashtracker.feature.settings.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.naji.cashtracker.navigation.SettingsRoute

fun NavGraphBuilder.settingsGraph(
    navController: NavController,
    onNavigateToProfile: () -> Unit
) {
    composable<SettingsRoute> {
        Text("Settings")
    }
}
