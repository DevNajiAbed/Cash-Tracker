package com.naji.cashtracker.core.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.naji.cashtracker.core.domain.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "auth_prefs")

private val userIdKey = stringPreferencesKey("user_id")
private val userNameKey = stringPreferencesKey("user_name")
private val userEmailKey = stringPreferencesKey("user_email")
private val userPhoneKey = stringPreferencesKey("user_phone")
private val userBioKey = stringPreferencesKey("user_bio")
private val userPhotoKey = stringPreferencesKey("user_photo")

class DataStoreAuthDataSource(
    private val context: Context
) {
    suspend fun saveUser(user: User) {
        context.dataStore.edit { prefs ->
            prefs[userIdKey] = user.id
            prefs[userNameKey] = user.name
            prefs[userEmailKey] = user.email
            prefs[userPhoneKey] = user.phone
            prefs[userBioKey] = user.bio
            prefs[userPhotoKey] = user.photoUri ?: ""
        }
    }

    fun getUser(): Flow<User?> = context.dataStore.data.map { prefs ->
        val id = prefs[userIdKey] ?: return@map null
        User(
            id = id,
            name = prefs[userNameKey] ?: "",
            email = prefs[userEmailKey] ?: "",
            phone = prefs[userPhoneKey] ?: "",
            bio = prefs[userBioKey] ?: "",
            photoUri = prefs[userPhotoKey]?.takeIf { it.isNotEmpty() }
        )
    }

    suspend fun deleteUser() {
        context.dataStore.edit { it.clear() }
    }
}
