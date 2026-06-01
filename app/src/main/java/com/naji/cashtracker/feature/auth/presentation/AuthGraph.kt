package com.naji.cashtracker.feature.auth.presentation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.naji.cashtracker.feature.auth.presentation.register.RegisterRoot
import com.naji.cashtracker.navigation.RegisterRoute

fun NavGraphBuilder.registerGraph(
    navController: NavController,
    onNavigateToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    composable<RegisterRoute> {
        RegisterRoot(
            onNavigateToHome = onNavigateToHome,
            modifier = modifier
        )
    }
}
