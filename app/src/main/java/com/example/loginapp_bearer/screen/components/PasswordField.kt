package com.example.loginapp_bearer.screen.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation

import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun PasswordField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    error: String? = null,
    onNewValue: (String) -> Unit,
) {
    var isVisible by remember { mutableStateOf(false) }

    val icon =
        if (isVisible) Icons.Default.VisibilityOff
        else Icons.Default.Visibility

    val visualTransformation =
        if (isVisible) VisualTransformation.None
        else PasswordVisualTransformation()

    OutlinedTextField(
        modifier = modifier,
        isError = error != null,
        supportingText = { error?.let { Text(it) } },
        value = value,
        onValueChange = {
            onNewValue(it.replace("\\s".toRegex(), ""))
        },
        placeholder = { Text(placeholder) },
        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock") },
        trailingIcon = {
            IconButton(onClick = { isVisible = !isVisible }) {
                Icon(imageVector = icon, contentDescription = "Visibility")
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = visualTransformation
    )
}