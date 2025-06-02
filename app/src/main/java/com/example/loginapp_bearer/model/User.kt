package com.example.loginapp_bearer.model


data class User(
    val id: Int,
    val name: String,
    val email: String,
    val divisionId: Int,
    val division: Division,
    val role: Int
)