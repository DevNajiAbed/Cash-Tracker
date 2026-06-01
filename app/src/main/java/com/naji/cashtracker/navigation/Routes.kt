package com.naji.cashtracker.navigation

import kotlinx.serialization.Serializable

@Serializable
object SplashRoute

@Serializable
object OnboardingRoute

@Serializable
object RegisterRoute

@Serializable
object HomeRoute

@Serializable
object AddTransactionRoute

@Serializable
data class EditTransactionRoute(val id: String)

@Serializable
object TransactionListRoute

@Serializable
object TransactionsRoute

@Serializable
object AnalyticsRoute

@Serializable
object CategoriesRoute

@Serializable
object CategoryListRoute

@Serializable
object AddCategoryRoute

@Serializable
data class EditCategoryRoute(val id: String)

@Serializable
object BudgetsRoute

@Serializable
object BudgetListRoute

@Serializable
object AddBudgetRoute

@Serializable
data class EditBudgetRoute(val id: String)

@Serializable
object SettingsRoute

@Serializable
object ProfileRoute

@Serializable
object EditProfileRoute
