package com.naji.cashtracker.feature.auth.domain

import com.naji.cashtracker.core.domain.Error

sealed interface AuthError : Error {
    enum class Field { IMAGE, NAME, EMAIL, PHONE, BIO }

    data class Validation(val field: Field, val message: String) : AuthError
    data object UserNotFound : AuthError
}
