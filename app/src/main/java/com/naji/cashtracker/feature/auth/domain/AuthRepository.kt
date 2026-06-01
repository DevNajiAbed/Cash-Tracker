package com.naji.cashtracker.feature.auth.domain

import com.naji.cashtracker.core.domain.EmptyResult
import com.naji.cashtracker.core.domain.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun register(
        photoUri: String,
        name: String,
        email: String,
        phone: String,
        bio: String
    ): EmptyResult<AuthError>
    fun getLoggedInUser(): Flow<User?>
    suspend fun getLoggedInUserOnce(): User?
    suspend fun logout()
}
