package com.example.loginapp_bearer.repository

import android.util.Log
import com.example.loginapp_bearer.api.TodoAPI
import com.example.loginapp_bearer.manager.TokenManager
import com.example.loginapp_bearer.model.LoginRequest
import com.example.loginapp_bearer.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface AuthRepository {
    suspend fun logIn(email: String, password: String)
    suspend fun getCurrentUser(): User?
}

class AuthRepositoryImpl @Inject constructor(
    private val todoApi: TodoAPI,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun logIn(email: String, password: String) =
        withContext(Dispatchers.IO) {
            try {
                tokenManager.clearToken()
                val response = todoApi.login(LoginRequest(email, password))
                if (response.isSuccessful) {
                    response.body()?.token?.let {
                        tokenManager.saveToken(it)
                    } ?: throw Exception("トークンが取得できませんでした")
                } else {
                    throw Exception("ログインに失敗しました: ${response.code()}")
                }
            } catch (e: Exception) {
                throw e
            }
        }

    override suspend fun getCurrentUser(): User? = withContext(Dispatchers.IO) {
        return@withContext try {
            val token = tokenManager.getToken()

            if (token.isNullOrEmpty()) {
                return@withContext null
            }

            val response = todoApi.getCurrentUser("Bearer $token")
            if (response.isSuccessful) {
                response.body()?.user
            } else {
                null
            }
        } catch (e: Exception) {
            throw e
        }
    }
}





