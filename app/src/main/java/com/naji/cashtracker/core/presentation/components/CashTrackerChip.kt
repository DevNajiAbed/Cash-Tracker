package com.naji.cashtracker.core.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naji.cashtracker.ui.theme.ChipShape
import com.naji.cashtracker.ui.theme.LightOnPrimaryContainer
import com.naji.cashtracker.ui.theme.LightOnSurfaceVariant
import com.naji.cashtracker.ui.theme.LightPrimaryContainer
import com.naji.cashtracker.ui.theme.LightSurfaceVariant

@Composable
fun CashTrackerChip(
    text: String,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    Surface(
        modifier = modifier.height(32.dp),
        shape = ChipShape,
        color = if (selected) LightPrimaryContainer else LightSurfaceVariant,
        onClick = onClick ?: {}
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 14.dp),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = if (selected) LightOnPrimaryContainer else LightOnSurfaceVariant
        )
    }
}
