package com.naji.cashtracker.feature.auth.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.naji.cashtracker.navigation.RegisterRoute

fun NavGraphBuilder.registerGraph(
    navController: NavController
) {
    composable<RegisterRoute> {
        Text("Register")
    }
}
