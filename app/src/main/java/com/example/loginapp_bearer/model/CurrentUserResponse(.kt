package com.example.loginapp_bearer.model

import kotlinx.serialization.Serializable

@Serializable
data class CurrentUserResponse(
    val user: User
)
