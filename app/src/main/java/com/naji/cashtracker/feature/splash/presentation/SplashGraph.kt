package com.naji.cashtracker.feature.splash.presentation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.naji.cashtracker.feature.splash.presentation.splash_screen.SplashRoot
import com.naji.cashtracker.navigation.HomeRoute
import com.naji.cashtracker.navigation.OnboardingRoute
import com.naji.cashtracker.navigation.SplashRoute

fun NavGraphBuilder.splashGraph(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    composable<SplashRoute> {
        SplashRoot(
            onNavigateToOnboarding = { navController.navigate(OnboardingRoute) },
            onNavigateToHome = { navController.navigate(HomeRoute) },
            modifier = modifier
        )
    }
}
