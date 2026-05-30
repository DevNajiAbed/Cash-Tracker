package com.naji.cashtracker.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.naji.cashtracker.ui.theme.CardShape

@Composable
fun CashTrackerCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        shape = CardShape,
        colors = CardDefaults.cardColors(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            content()
        }
    }
}
