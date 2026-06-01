package com.naji.cashtracker.feature.auth.data

import android.content.Context
import android.net.Uri
import com.naji.cashtracker.core.data.DataStoreAuthDataSource
import com.naji.cashtracker.core.domain.EmptyResult
import com.naji.cashtracker.core.domain.Result
import com.naji.cashtracker.core.domain.User
import com.naji.cashtracker.feature.auth.domain.AuthError
import com.naji.cashtracker.feature.auth.domain.AuthRepository
import java.io.File
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.util.UUID

class AuthRepositoryImpl(
    private val dataSource: DataStoreAuthDataSource,
    private val context: Context
) : AuthRepository {

    override suspend fun register(
        photoUri: String,
        name: String,
        email: String,
        phone: String,
        bio: String
    ): EmptyResult<AuthError> {
        if(photoUri.trim().isBlank()) {
            return Result.Error(
                AuthError.Validation(AuthError.Field.IMAGE, "Image cannot be empty")
            )
        }

        val trimmedName = name.trim()
        if (trimmedName.length < 2) {
            return Result.Error(
                AuthError.Validation(AuthError.Field.NAME, "Name must be at least 2 characters")
            )
        }

        val trimmedEmail = email.trim()
        if (!isValidEmail(trimmedEmail)) {
            return Result.Error(
                AuthError.Validation(AuthError.Field.EMAIL, "Enter a valid email address")
            )
        }

        if (phone.trim().isBlank()) {
            return Result.Error(
                AuthError.Validation(AuthError.Field.PHONE, "Phone number cannot be empty")
            )
        }

        if (bio.trim().isBlank()) {
            return Result.Error(
                AuthError.Validation(AuthError.Field.BIO, "Bio cannot be empty")
            )
        }

        val persistentPhotoUri = saveAvatarToInternalStorage(photoUri)

        val user = User(
            id = UUID.randomUUID().toString(),
            name = trimmedName,
            email = trimmedEmail,
            phone = phone.trim(),
            bio = bio.trim(),
            photoUri = persistentPhotoUri
        )

        dataSource.saveUser(user)
        return Result.Success(Unit)
    }

    private fun saveAvatarToInternalStorage(contentUri: String): String? {
        return try {
            val uri = Uri.parse(contentUri)
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val dir = File(context.filesDir, "avatars")
            dir.mkdirs()
            val file = File(dir, "avatar_${UUID.randomUUID()}.jpg")
            file.outputStream().use { output -> inputStream.copyTo(output) }
            inputStream.close()
            Uri.fromFile(file).toString()
        } catch (_: Exception) {
            null
        }
    }

    override fun getLoggedInUser(): Flow<User?> = dataSource.getUser()

    override suspend fun getLoggedInUserOnce(): User? {
        return dataSource.getUser().first()
    }

    override suspend fun logout() {
        dataSource.deleteUser()
    }

    private fun isValidEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".") && email.length >= 5
    }
}
