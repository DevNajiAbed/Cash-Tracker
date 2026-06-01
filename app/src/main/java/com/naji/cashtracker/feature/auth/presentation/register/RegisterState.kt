package com.naji.cashtracker.feature.auth.presentation.register

import com.naji.cashtracker.core.presentation.UiText

data class RegisterState(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val bio: String = "",
    val photoUri: String = "",
    val photoError: UiText? = null,
    val nameError: UiText? = null,
    val emailError: UiText? = null,
    val phoneError: UiText? = null,
    val bioError: UiText? = null,
    val isLoading: Boolean = false,
    val error: UiText? = null
)
