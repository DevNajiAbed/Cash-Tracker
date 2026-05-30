package com.naji.cashtracker.feature.splash.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.naji.cashtracker.core.presentation.components.CashTrackerCard

@Composable
fun SplashRoot(
    onNavigateToOnboarding: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    SplashScreen()
}

@Composable
fun SplashScreen() {
    CashTrackerCard {
        Text("Splash")
    }
}
