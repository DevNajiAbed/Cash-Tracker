package com.naji.cashtracker.feature.transaction.presentation.addedit

import androidx.compose.ui.graphics.Color
import com.naji.cashtracker.ui.theme.LightError
import com.naji.cashtracker.ui.theme.LightHealth
import com.naji.cashtracker.ui.theme.LightInfo
import com.naji.cashtracker.ui.theme.LightPink
import com.naji.cashtracker.ui.theme.LightPrimary
import com.naji.cashtracker.ui.theme.LightSecondary
import com.naji.cashtracker.ui.theme.LightTertiary

enum class TransactionType {
    Income, Expense
}

data class CategoryUi(
    val id: String,
    val name: String,
    val color: Color
)

data class AddTransactionState(
    val transactionType: TransactionType = TransactionType.Income,
    val amount: String = "",
    val selectedCategory: String? = null,
    val note: String = "",
    val selectedDateMillis: Long? = null,
    val dateText: String = "Today",
    val categories: List<CategoryUi> = emptyList(),
    val isSaving: Boolean = false,
    val showDatePicker: Boolean = false
)

fun sampleCategories(type: TransactionType): List<CategoryUi> = listOf(
    CategoryUi("1", "Food", LightError),
    CategoryUi("2", "Transport", LightTertiary),
    CategoryUi("3", "Bills", LightPrimary),
    CategoryUi("4", "Shopping", LightSecondary),
    CategoryUi("5", "Health", LightHealth),
    CategoryUi(
        "6",
        if (type == TransactionType.Income) "Salary" else "Entertain",
        if (type == TransactionType.Income) LightInfo else LightPink
    )
)
