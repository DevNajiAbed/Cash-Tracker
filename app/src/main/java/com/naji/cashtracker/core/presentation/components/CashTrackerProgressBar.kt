package com.naji.cashtracker.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.naji.cashtracker.ui.theme.LightPrimary
import com.naji.cashtracker.ui.theme.LightSurfaceVariant

@Composable
fun CashTrackerProgressBar(
    progress: Float,
    modifier: Modifier = Modifier
) {
    LinearProgressIndicator(
        progress = { progress },
        modifier = modifier
            .fillMaxWidth()
            .height(10.dp)
            .clip(RoundedCornerShape(999.dp)),
        color = LightPrimary,
        trackColor = LightSurfaceVariant
    )
}
