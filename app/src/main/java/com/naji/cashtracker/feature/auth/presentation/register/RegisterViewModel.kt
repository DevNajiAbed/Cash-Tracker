package com.naji.cashtracker.feature.auth.presentation.register

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naji.cashtracker.core.data.UserPreferences
import com.naji.cashtracker.core.domain.onFailure
import com.naji.cashtracker.core.domain.onSuccess
import com.naji.cashtracker.core.presentation.UiText
import com.naji.cashtracker.core.presentation.UiText.*
import com.naji.cashtracker.feature.auth.domain.AuthError
import com.naji.cashtracker.feature.auth.domain.AuthRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository,
    private val userPreferences: UserPreferences,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(
        RegisterState(
            name = savedStateHandle["name"] ?: "",
            email = savedStateHandle["email"] ?: "",
            phone = savedStateHandle["phone"] ?: "",
            bio = savedStateHandle["bio"] ?: ""
        )
    )
    val state = _state.asStateFlow()

    private val _events = Channel<RegisterEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.OnNameChange -> {
                savedStateHandle["name"] = action.name
                _state.update { it.copy(name = action.name, nameError = null) }
            }
            is RegisterAction.OnEmailChange -> {
                savedStateHandle["email"] = action.email
                _state.update { it.copy(email = action.email, emailError = null) }
            }
            is RegisterAction.OnPhoneChange -> {
                savedStateHandle["phone"] = action.phone
                _state.update { it.copy(phone = action.phone) }
            }
            is RegisterAction.OnBioChange -> {
                savedStateHandle["bio"] = action.bio
                _state.update { it.copy(bio = action.bio) }
            }
            is RegisterAction.OnPhotoPicked -> {
                _state.update { it.copy(photoUri = action.uri) }
            }
            is RegisterAction.OnRegister -> register()
        }
    }

    private fun clearErrors() {
        _state.update { it.copy(nameError = null, emailError = null, error = null) }
    }

    private fun register() {
        val s = _state.value
        clearErrors()

        if (s.name.isBlank()) {
            _state.update { it.copy(nameError = DynamicString("Name is required")) }
            return
        }

        if (s.email.isBlank() || !s.email.contains("@")) {
            _state.update { it.copy(emailError = DynamicString("Enter a valid email")) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            authRepository.register(
                name = s.name,
                email = s.email,
                phone = s.phone,
                bio = s.bio,
                photoUri = s.photoUri
            ).onSuccess {
                userPreferences.setFirstLaunchComplete()
                _state.update { it.copy(isLoading = false) }
                _events.send(RegisterEvent.NavigateToHome)
            }.onFailure { error ->
                _state.update { it.copy(isLoading = false) }
                when (error) {
                    is AuthError.Validation -> {
                        when (error.field) {
                            AuthError.Field.IMAGE -> _state.update { it.copy(photoError = DynamicString(error.message)) }
                            AuthError.Field.NAME -> _state.update { it.copy(nameError = DynamicString(error.message)) }
                            AuthError.Field.EMAIL -> _state.update { it.copy(emailError = DynamicString(error.message)) }
                            AuthError.Field.PHONE -> _state.update { it.copy(phoneError = DynamicString(error.message)) }
                            AuthError.Field.BIO -> _state.update { it.copy(bioError = DynamicString(error.message)) }
                        }
                    }
                    else -> {
                        _events.send(RegisterEvent.ShowToast(DynamicString("Something went wrong")))
                    }
                }
            }
        }
    }
}
