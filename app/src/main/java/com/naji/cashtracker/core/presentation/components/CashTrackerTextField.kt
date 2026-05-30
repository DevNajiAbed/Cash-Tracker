package com.naji.cashtracker.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.naji.cashtracker.ui.theme.InputShape
import com.naji.cashtracker.ui.theme.LightError
import com.naji.cashtracker.ui.theme.LightOnSurface
import com.naji.cashtracker.ui.theme.LightOnSurfaceVariant
import com.naji.cashtracker.ui.theme.LightOutline
import com.naji.cashtracker.ui.theme.LightPrimary

@Composable
fun CashTrackerTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    placeholder: String = "",
    isError: Boolean = false,
    errorMessage: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = true
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = InputShape,
        label = if (label.isNotEmpty()) { { Text(text = label) } } else null,
        placeholder = if (placeholder.isNotEmpty()) { { Text(text = placeholder) } } else null,
        isError = isError,
        supportingText = if (errorMessage != null) { { Text(text = errorMessage) } } else null,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        singleLine = singleLine,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = LightPrimary,
            unfocusedBorderColor = LightOutline,
            cursorColor = LightPrimary,
            focusedLabelColor = LightPrimary,
            unfocusedLabelColor = LightOnSurfaceVariant,
            focusedTextColor = LightOnSurface,
            unfocusedTextColor = LightOnSurface,
            errorBorderColor = LightError,
            errorLabelColor = LightError,
            errorCursorColor = LightError
        )
    )
}
