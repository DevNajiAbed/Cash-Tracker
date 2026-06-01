package com.naji.cashtracker.feature.auth.presentation.register

sealed interface RegisterAction {
    data class OnNameChange(val name: String) : RegisterAction
    data class OnEmailChange(val email: String) : RegisterAction
    data class OnPhoneChange(val phone: String) : RegisterAction
    data class OnBioChange(val bio: String) : RegisterAction
    data class OnPhotoPicked(val uri: String) : RegisterAction
    data object OnRegister : RegisterAction
}
