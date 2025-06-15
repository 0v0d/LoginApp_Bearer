package com.example.loginapp_bearer.navigation

import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
data class Home(val userName: String)