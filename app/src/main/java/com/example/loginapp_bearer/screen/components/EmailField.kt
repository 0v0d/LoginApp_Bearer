package com.example.loginapp_bearer.screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun EmailField(
    modifier: Modifier = Modifier,
    value: String,
    error: String? = null,
    onNewValue: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        isError = error != null,
        supportingText = { error?.let { Text(it) } },
        onValueChange = { onNewValue(it.replace("\\s".toRegex(), "")) },
        placeholder = { Text("メールアドレス") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Email"
            )
        },
        modifier = modifier.fillMaxWidth()
    )
}