package com.example.loginapp_bearer.navigation

import androidx.navigation.NavHostController


fun NavHostController.navigateSingleTop(route: Any) {
    this.navigate(route) {
        launchSingleTop = true
    }
}