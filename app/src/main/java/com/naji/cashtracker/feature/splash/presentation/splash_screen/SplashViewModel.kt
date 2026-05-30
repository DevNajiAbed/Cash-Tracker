package com.naji.cashtracker.feature.splash.presentation.splash_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naji.cashtracker.core.data.UserPreferences
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashViewModel(
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _events = MutableSharedFlow<SplashEvent>()
    val events = _events.asSharedFlow()

    init {
        viewModelScope.launch {
            delay(1500L)
            val isFirstLaunch = userPreferences.isFirstLaunch.first()
            val event = if (isFirstLaunch) SplashEvent.NavigateToOnboarding
            else SplashEvent.NavigateToHome
            _events.emit(event)
        }
    }
}
