package com.example.loginapp_bearer.manager

import android.content.Context
import androidx.core.content.edit


class TokenManager(context: Context) {
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
