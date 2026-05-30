package com.naji.cashtracker.feature.analytics.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.naji.cashtracker.navigation.AnalyticsRoute

fun NavGraphBuilder.analyticsGraph(
    navController: NavController
) {
    composable<AnalyticsRoute> {
        Text("Analytics")
    }
}
