package com.naji.cashtracker.feature.onboarding.presentation.onboarding_screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class OnboardingViewModel : ViewModel() {

    private val _events = MutableSharedFlow<OnboardingEvent>()
    val events = _events.asSharedFlow()

    fun onAction(action: OnboardingAction) {
        when (action) {
            OnboardingAction.OnComplete -> {
                _events.tryEmit(OnboardingEvent.NavigateToRegister)
            }
        }
    }
}
