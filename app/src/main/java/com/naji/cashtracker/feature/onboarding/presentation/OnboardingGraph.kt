package com.naji.cashtracker.feature.onboarding.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.naji.cashtracker.feature.onboarding.presentation.onboarding_screen.OnboardingRoot
import com.naji.cashtracker.navigation.OnboardingRoute

fun NavGraphBuilder.onboardingGraph(
    navController: NavController,
    onComplete: () -> Unit
) {
    composable<OnboardingRoute> {
        OnboardingRoot(onComplete = onComplete)
    }
}
