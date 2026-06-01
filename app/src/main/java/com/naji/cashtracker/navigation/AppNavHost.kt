package com.naji.cashtracker.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.naji.cashtracker.feature.auth.presentation.registerGraph
import com.naji.cashtracker.feature.budget.presentation.budgetGraph
import com.naji.cashtracker.feature.category.presentation.categoryGraph
import com.naji.cashtracker.feature.main.presentation.mainGraph
import com.naji.cashtracker.feature.onboarding.presentation.onboardingGraph
import com.naji.cashtracker.feature.profile.presentation.editProfileGraph
import com.naji.cashtracker.feature.profile.presentation.profileGraph
import com.naji.cashtracker.feature.splash.presentation.splashGraph
import com.naji.cashtracker.feature.transaction.presentation.addedit.AddTransactionRoot

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = SplashRoute,
        modifier = Modifier
            .fillMaxSize()
    ) {
        splashGraph(
            navController = navController,
            modifier = modifier
        )
        onboardingGraph(
            navController = navController,
            onComplete = { navController.navigate(RegisterRoute) },
            modifier = modifier
        )
        registerGraph(
            navController = navController,
            onNavigateToHome = {
                navController.navigate(HomeRoute) {
                    popUpTo(SplashRoute) { inclusive = true }
                }
            },
            modifier = modifier
        )

        mainGraph(
            navController = navController,
            onNavigateToAddTransaction = { navController.navigate(AddTransactionRoute) },
            onNavigateToCategories = { navController.navigate(CategoriesRoute) },
            onNavigateToBudgets = { navController.navigate(BudgetsRoute) },
            onNavigateToProfile = { navController.navigate(ProfileRoute) }
        )

        categoryGraph(navController = navController)
        budgetGraph(
            navController = navController,
            onNavigateToAddBudget = { navController.navigate(AddBudgetRoute) }
        )
        profileGraph(navController = navController)
        editProfileGraph(navController = navController)

        composable<AddTransactionRoute> {
            AddTransactionRoot(onNavigateBack = { navController.popBackStack() })
        }
        composable<EditTransactionRoute> {
            Text("Edit Transaction")
        }
    }
}
