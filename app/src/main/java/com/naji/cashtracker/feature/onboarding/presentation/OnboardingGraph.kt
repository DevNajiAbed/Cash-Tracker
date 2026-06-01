package com.naji.cashtracker.feature.onboarding.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.naji.cashtracker.feature.onboarding.presentation.onboarding_screen.OnboardingRoot
import com.naji.cashtracker.navigation.OnboardingRoute

fun NavGraphBuilder.onboardingGraph(
    navController: NavController,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    composable<OnboardingRoute> {
        OnboardingRoot(
            onComplete = onComplete,
            modifier = modifier
        )
    }
}
