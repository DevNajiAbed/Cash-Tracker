package com.naji.cashtracker.feature.splash.presentation.splash_screen

sealed interface SplashEvent {
    data object NavigateToOnboarding : SplashEvent
    data object NavigateToHome : SplashEvent
}
