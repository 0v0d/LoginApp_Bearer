package com.example.loginapp_bearer.utils

import com.example.loginapp_bearer.extensions.isValidEmail
import com.example.loginapp_bearer.extensions.isValidPassword
import com.example.loginapp_bearer.state.AuthFormState

object AuthValidator {
    fun validate(
        email: String,
        password: String,
    ): AuthFormState {
        var formState = AuthFormState()

        if (!email.isValidEmail()) {
            formState = formState.copy(
                emailError = "メールアドレスが無効です"
            )
        }

        if (!password.isValidPassword()) {
            formState =
                formState.copy(
                    passwordError = "パスワードは6文字以上20文字以下である必要があります"
                )
        }

        return formState
    }
}