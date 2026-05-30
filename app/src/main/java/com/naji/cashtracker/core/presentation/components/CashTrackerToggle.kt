package com.naji.cashtracker.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naji.cashtracker.ui.theme.LightOnPrimary
import com.naji.cashtracker.ui.theme.LightOnSurface
import com.naji.cashtracker.ui.theme.LightPrimary
import com.naji.cashtracker.ui.theme.LightSurfaceVariant

@Composable
fun CashTrackerToggle(
    options: List<String>,
    selectedIndex: Int,
    onSelectionChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(999.dp)),
        shape = RoundedCornerShape(999.dp),
        color = LightSurfaceVariant
    ) {
        Row(
            modifier = Modifier.padding(3.dp)
        ) {
            options.forEachIndexed { index, option ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(999.dp))
                        .background(
                            if (selectedIndex == index) LightPrimary else Color.Transparent
                        )
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = option,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (selectedIndex == index) LightOnPrimary else LightOnSurface
                    )
                }
            }
        }
    }
}
