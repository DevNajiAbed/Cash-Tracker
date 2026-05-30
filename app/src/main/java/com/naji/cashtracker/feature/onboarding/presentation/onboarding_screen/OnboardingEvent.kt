package com.naji.cashtracker.feature.onboarding.presentation.onboarding_screen

sealed interface OnboardingEvent {
    data object NavigateToRegister : OnboardingEvent
}
