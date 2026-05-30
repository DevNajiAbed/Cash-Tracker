package com.naji.cashtracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.naji.cashtracker.feature.analytics.presentation.analyticsGraph
import com.naji.cashtracker.feature.auth.presentation.registerGraph
import com.naji.cashtracker.feature.budget.presentation.budgetGraph
import com.naji.cashtracker.feature.category.presentation.categoryGraph
import com.naji.cashtracker.feature.dashboard.presentation.dashboardGraph
import com.naji.cashtracker.feature.onboarding.presentation.onboardingGraph
import com.naji.cashtracker.feature.profile.presentation.editProfileGraph
import com.naji.cashtracker.feature.profile.presentation.profileGraph
import com.naji.cashtracker.feature.settings.presentation.settingsGraph
import com.naji.cashtracker.feature.splash.presentation.splashGraph
import com.naji.cashtracker.feature.transaction.presentation.transactionGraph

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = SplashRoute,
        modifier = modifier
    ) {
        splashGraph(navController = navController)
        onboardingGraph(navController = navController)
        registerGraph(navController = navController)
        dashboardGraph(
            navController = navController,
            onNavigateToAddTransaction = { navController.navigate(AddTransactionRoute) },
            onNavigateToCategories = { navController.navigate(CategoriesRoute) },
            onNavigateToBudgets = { navController.navigate(BudgetsRoute) }
        )
        transactionGraph(
            navController = navController,
            onNavigateToAddEdit = { id -> navController.navigate(AddTransactionRoute) }
        )
        analyticsGraph(navController = navController)
        categoryGraph(navController = navController)
        budgetGraph(
            navController = navController,
            onNavigateToAddBudget = { navController.navigate(AddBudgetRoute) }
        )
        settingsGraph(
            navController = navController,
            onNavigateToProfile = { navController.navigate(ProfileRoute) }
        )
        profileGraph(navController = navController)
        editProfileGraph(navController = navController)
    }
}
