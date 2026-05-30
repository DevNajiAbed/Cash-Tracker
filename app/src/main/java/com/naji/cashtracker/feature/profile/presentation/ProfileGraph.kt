package com.naji.cashtracker.feature.profile.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.naji.cashtracker.navigation.EditProfileRoute
import com.naji.cashtracker.navigation.ProfileRoute

fun NavGraphBuilder.profileGraph(
    navController: NavController
) {
    composable<ProfileRoute> {
        Text("Profile")
    }
}

fun NavGraphBuilder.editProfileGraph(
    navController: NavController
) {
    composable<EditProfileRoute> {
        Text("Edit Profile")
    }
}
