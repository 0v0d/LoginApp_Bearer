package com.example.loginapp_bearer.navigation

import kotlinx.serialization.Serializable

sealed class Destination{
    @Serializable
    object Login

    @Serializable
    data class Home(val userName: String)
}
