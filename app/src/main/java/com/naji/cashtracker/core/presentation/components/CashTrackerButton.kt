package com.naji.cashtracker.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naji.cashtracker.ui.theme.ButtonShape
import com.naji.cashtracker.ui.theme.LightOnPrimary
import com.naji.cashtracker.ui.theme.LightPrimary
import com.naji.cashtracker.ui.theme.LightSurfaceVariant

@Composable
fun CashTrackerPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        enabled = enabled,
        shape = ButtonShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = LightPrimary,
            contentColor = LightOnPrimary,
            disabledContainerColor = LightSurfaceVariant,
            disabledContentColor = Color.Gray
        ),
        contentPadding = PaddingValues(horizontal = 24.dp)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = if (enabled) LightOnPrimary else Color.Gray
        )
    }
}

@Composable
fun CashTrackerSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        enabled = enabled,
        shape = ButtonShape,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = LightPrimary
        ),
        border = BorderStroke(2.dp, if (enabled) LightPrimary else LightSurfaceVariant),
        contentPadding = PaddingValues(horizontal = 24.dp)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
