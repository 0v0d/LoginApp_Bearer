package com.example.loginapp_bearer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.loginapp_bearer.navigation.AppNavigation
import com.example.loginapp_bearer.ui.theme.LoginApp_BearerTheme

@Composable
fun LoginApp(modifier: Modifier = Modifier) {
    LoginApp_BearerTheme {
        Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
            AppNavigation(modifier.padding(innerPadding))
        }
    }
}

