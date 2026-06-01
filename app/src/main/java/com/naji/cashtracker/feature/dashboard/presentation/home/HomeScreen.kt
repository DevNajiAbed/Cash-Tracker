package com.naji.cashtracker.feature.dashboard.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.naji.cashtracker.core.presentation.ObserveAsEvents
import com.naji.cashtracker.R
import com.naji.cashtracker.core.presentation.components.CashTrackerFab
import com.naji.cashtracker.ui.theme.CashTrackerTheme
import com.naji.cashtracker.ui.theme.CardShape
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeRoot(
    onNavigateToAddTransaction: () -> Unit,
    onNavigateToCategories: () -> Unit,
    onNavigateToBudgets: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            HomeEvent.NavigateToAddTransaction -> onNavigateToAddTransaction()
            HomeEvent.NavigateToCategories -> onNavigateToCategories()
            HomeEvent.NavigateToBudgets -> onNavigateToBudgets()
        }
    }

    HomeScreen(
        state = state,
        onAction = viewModel::onAction,
        modifier = modifier
    )
}

@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            BalanceCard(
                balance = state.balance,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            StatsRow(
                income = state.income,
                expenses = state.expenses,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            QuickNavCards(
                onCategoriesClick = { onAction(HomeAction.OnCategoriesClick) },
                onBudgetsClick = { onAction(HomeAction.OnBudgetsClick) },
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            SectionHeader(title = stringResource(R.string.home_recent_transactions))

            state.recentTransactions.forEach { tx ->
                TransactionRow(
                    transaction = tx,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(80.dp))
        }

        CashTrackerFab(
            icon = Icons.Rounded.Add,
            onClick = { onAction(HomeAction.OnAddTransaction) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 20.dp),
            contentDescription = stringResource(R.string.home_add_transaction)
        )
    }
}

@Composable
private fun BalanceCard(
    balance: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(CardShape)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                    )
                )
            )
            .padding(horizontal = 24.dp, vertical = 20.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Column {
            Text(
                text = stringResource(R.string.home_balance_label),
                fontSize = 13.sp,
                color = Color.White.copy(alpha = 0.8f)
            )
            Text(
                text = balance,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
private fun StatsRow(
    income: String,
    expenses: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        StatCard(
            label = stringResource(R.string.home_income_label),
            value = income,
            valueColor = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.weight(1f)
        )
        StatCard(
            label = stringResource(R.string.home_expenses_label),
            value = expenses,
            valueColor = MaterialTheme.colorScheme.error,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun StatCard(
    label: String,
    value: String,
    valueColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(14.dp)
    ) {
        Column {
            Text(
                text = label,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = valueColor
            )
        }
    }
}

@Composable
private fun QuickNavCards(
    onCategoriesClick: () -> Unit,
    onBudgetsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        QuickNavCard(
            icon = "#",
            iconBackground = MaterialTheme.colorScheme.primary,
            label = stringResource(R.string.home_categories_label),
            onClick = onCategoriesClick,
            modifier = Modifier.weight(1f)
        )
        QuickNavCard(
            icon = "$",
            iconBackground = MaterialTheme.colorScheme.tertiary,
            label = stringResource(R.string.home_budgets_label),
            onClick = onBudgetsClick,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun QuickNavCard(
    icon: String,
    iconBackground: Color,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(35.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(iconBackground),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = icon,
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label,
            modifier = Modifier.weight(1f),
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "\u203A",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
private fun TransactionRow(
    transaction: TransactionUi,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Color(transaction.iconColor)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = transaction.icon,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = transaction.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = transaction.subtitle,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Text(
            text = transaction.amount,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (transaction.isPositive) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.error
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    CashTrackerTheme {
        HomeScreen(
            state = HomeState(
                balance = "$12,430.00",
                income = "+$5,240",
                expenses = "-$3,180",
                recentTransactions = listOf(
                    TransactionUi("1", "F", 0xFF10B981, "Food", "Today, 2:30 PM", "-$45", false),
                    TransactionUi("2", "S", 0xFF4F46E5, "Salary", "Today, 9:00 AM", "+$3,500", true),
                    TransactionUi("3", "T", 0xFFF59E0B, "Transport", "Yesterday", "-$12", false)
                )
            ),
            onAction = {}
        )
    }
}
