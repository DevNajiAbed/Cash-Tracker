package com.naji.cashtracker.feature.onboarding.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.naji.cashtracker.navigation.OnboardingRoute

fun NavGraphBuilder.onboardingGraph(
    navController: NavController
) {
    composable<OnboardingRoute> {
        Text("Onboarding")
    }
}
