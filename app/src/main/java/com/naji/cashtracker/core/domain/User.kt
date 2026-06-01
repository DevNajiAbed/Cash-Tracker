package com.naji.cashtracker.core.domain

data class User(
    val id: String,
    val name: String,
    val email: String,
    val phone: String = "",
    val bio: String = "",
    val photoUri: String? = null
)
