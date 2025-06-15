package com.example.loginapp_bearer.api

import com.example.loginapp_bearer.model.CurrentUserResponse
import com.example.loginapp_bearer.model.LoginRequest
import com.example.loginapp_bearer.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface TodoAPI {
    @POST("v1/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    @GET("v1/users/me")
    suspend fun getCurrentUser(
        @Header("Authorization") authorization: String
    ): Response<CurrentUserResponse>
}