package com.naji.cashtracker.feature.category.presentation

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.naji.cashtracker.navigation.AddCategoryRoute
import com.naji.cashtracker.navigation.CategoryListRoute
import com.naji.cashtracker.navigation.CategoriesRoute
import com.naji.cashtracker.navigation.EditCategoryRoute

fun NavGraphBuilder.categoryGraph(
    navController: NavController
) {
    navigation<CategoriesRoute>(startDestination = CategoryListRoute) {
        composable<CategoryListRoute> {
            Text("Categories")
        }
        composable<AddCategoryRoute> {
            Text("Add Category")
        }
        composable<EditCategoryRoute> {
            Text("Edit Category")
        }
    }
}
