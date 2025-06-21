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
        startDestination = Destination.Login,
        modifier = modifier
    ) {
        composable<Destination.Login> {
            LoginScreen(
                viewModel = viewModel,
                onNavigateToHome = { userName ->
                    navController.navigateSingleTop(Destination.Home(userName = userName))
                }
            )
        }
        composable<Destination.Home> { backStackEntry ->
            val home: Destination.Home = backStackEntry.toRoute()
            HomeScreen(userName = home.userName)
        }
    }
}

