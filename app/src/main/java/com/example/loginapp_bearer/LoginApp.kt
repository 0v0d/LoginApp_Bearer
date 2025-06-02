package com.example.loginapp_bearer

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.loginapp_bearer.model.User
import com.example.loginapp_bearer.ui.theme.LoginApp_BearerTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Inject
import androidx.core.content.edit
import dagger.Module
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

@Composable
fun LoginApp(modifier: Modifier = Modifier) {
    LoginApp_BearerTheme {
        Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
            AppNavigation(modifier.padding(innerPadding))
        }
    }
}


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
                onNavigateToHome = {
                    navController.navigateSingleTop(Login)
                }
            )
        }

    }
}

@Serializable
object Login

@Serializable
data class Home(val userName: String)

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel,
    onNavigateToHome: () -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {

    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.Lock,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "ログイン",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                EmailField(
                    value = email,
                    error = "formState.emailError",
                    onNewValue = { email = it },
                    modifier = Modifier.fillMaxWidth()
                )

                PasswordField(
                    value = password,
                    placeholder = "パスワード",
                    error = "formState.passwordError",
                    onNewValue = { password = it },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            Button(
                onClick = { onNavigateToHome() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(top = 8.dp),
            ) {
                Text("ログイン")
            }
        }
    }
}

@HiltViewModel
class AuthViewModel @Inject constructor(
) : ViewModel() {

}


interface AuthRepository {
    suspend fun logIn(email: String, password: String)
}

@Module
class NetWorkModule(){

}


class AuthRepositoryImpl(
    private val todoApi: TodoAPI,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun logIn(email: String, password: String) {
        withContext(Dispatchers.IO) {
            try {
                val res = todoApi.login(LoginRequest(email, password))

                res.body()?.token?.let {
                    tokenManager.saveToken(it)
                }
            } catch (e: Exception) {
                Log.e("RepositoryImpl",e.toString())
                null
            }
        }
    }
}

class TokenManager(private val context: Context) {
    private val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit { putString("access_token", token) }
    }

    fun getToken(): String? {
        return prefs.getString("access_token", null)
    }

    fun clearToken() {
        prefs.edit { remove("access_token") }
    }
}

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val token: String
)

interface TodoAPI {
    @POST("api/v1/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>
}

fun NavHostController.navigateSingleTop(route: Any) {
    this.navigate(route) {
        launchSingleTop = true
    }
}

sealed class AuthState {
    data object LoggedOut : AuthState()
    data class Login(val user: User) : AuthState()
}

