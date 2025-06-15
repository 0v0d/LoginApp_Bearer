package com.example.loginapp_bearer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.loginapp_bearer.screen.HomeScreen
import com.example.loginapp_bearer.screen.LoginScreen
import com.example.loginapp_bearer.viewmodel.AuthViewModel

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()

    NavHost(
        navController,
        startDestination = Login,
        modifier = modifier
    ) {
        composable<Login> {
            LoginScreen(
                viewModel = viewModel,
                onNavigateToHome = { userName ->
                    navController.navigateSingleTop(Home(userName = userName))
                }
            )
        }
        composable<Home> { backStackEntry ->
            val home: Home = backStackEntry.toRoute()
            HomeScreen(userName = home.userName)
        }
    }
}

