package com.example.loginapp_bearer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginapp_bearer.state.AuthFormState
import com.example.loginapp_bearer.model.User
import com.example.loginapp_bearer.repository.AuthRepository
import com.example.loginapp_bearer.utils.AuthValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _isLogin = MutableStateFlow(false)
    val isLogin = _isLogin.asStateFlow()

    private val _userInfo = MutableStateFlow<User?>(null)
    val userInfo = _userInfo.asStateFlow()

    private val _authFormState = MutableStateFlow(AuthFormState())
    val authFormState = _authFormState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val validationResult = AuthValidator.validate(
                    email,
                    password
                )
                if (validationResult != AuthFormState()) {
                    _authFormState.value = validationResult
                    return@launch
                }

                repository.logIn(email = email, password = password)
                _isLogin.value = true
            } catch (e: Exception) {
                _isLogin.value = false
                throw e
            }
        }
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            try {
                val user = repository.getCurrentUser()
                _userInfo.value = user
            } catch (e: Exception) {
                _userInfo.value = null
                throw e
            }
        }
    }
}