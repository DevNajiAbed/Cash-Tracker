package com.naji.cashtracker.feature.onboarding.presentation.onboarding_screen

sealed interface OnboardingAction {
    data object OnComplete : OnboardingAction
}
