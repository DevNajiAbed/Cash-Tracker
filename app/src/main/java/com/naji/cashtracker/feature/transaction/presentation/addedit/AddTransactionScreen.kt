@file:OptIn(ExperimentalMaterial3Api::class)

package com.naji.cashtracker.feature.transaction.presentation.addedit

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.naji.cashtracker.R
import com.naji.cashtracker.core.presentation.ObserveAsEvents
import com.naji.cashtracker.core.presentation.UiText
import com.naji.cashtracker.core.presentation.components.CashTrackerPrimaryButton
import com.naji.cashtracker.core.presentation.components.CashTrackerTextField
import com.naji.cashtracker.core.presentation.components.CashTrackerToggle
import com.naji.cashtracker.ui.theme.CashTrackerTheme
import com.naji.cashtracker.ui.theme.InputShape
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddTransactionRoot(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddTransactionViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            AddTransactionEvent.NavigateBack -> onNavigateBack()
            is AddTransactionEvent.ShowToast -> {
                val message = when (val msg = event.message) {
                    is UiText.DynamicString -> msg.value
                    is UiText.StringResource -> context.getString(msg.id, *msg.args)
                }
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    AddTransactionScreen(
        state = state,
        onAction = viewModel::onAction,
        modifier = modifier
    )
}

@Composable
fun AddTransactionScreen(
    state: AddTransactionState,
    onAction: (AddTransactionAction) -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = state.selectedDateMillis ?: System.currentTimeMillis()
        )
        DatePickerDialog(
            onDismissRequest = { onAction(AddTransactionAction.OnToggleDatePicker) },
            confirmButton = {
                TextButton(
                    onClick = {
                        onAction(AddTransactionAction.OnDateSelected(datePickerState.selectedDateMillis))
                    }
                ) {
                    Text(stringResource(R.string.add_tx_date_confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = { onAction(AddTransactionAction.OnToggleDatePicker) }) {
                    Text(stringResource(R.string.add_tx_date_cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 400.dp)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {
            HeaderRow(onClose = { onAction(AddTransactionAction.OnClose) })

            Spacer(modifier = Modifier.height(16.dp))

            CashTrackerToggle(
                options = listOf(
                    stringResource(R.string.add_tx_income),
                    stringResource(R.string.add_tx_expense)
                ),
                selectedIndex = if (state.transactionType == TransactionType.Income) 0 else 1,
                onSelectionChange = { index ->
                    onAction(
                        AddTransactionAction.OnTypeChange(
                            if (index == 0) TransactionType.Income else TransactionType.Expense
                        )
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            AmountInput(
                amount = state.amount,
                onAmountChange = { onAction(AddTransactionAction.OnAmountChange(it)) },
                isExpense = state.transactionType == TransactionType.Expense
            )

            Spacer(modifier = Modifier.height(24.dp))

            FieldLabel(text = stringResource(R.string.add_tx_category_label))

            Spacer(modifier = Modifier.height(8.dp))

            CategoryGrid(
                categories = state.categories,
                selectedId = state.selectedCategory,
                onSelect = { onAction(AddTransactionAction.OnCategorySelect(it)) }
            )

            Spacer(modifier = Modifier.height(20.dp))

            FieldLabel(text = stringResource(R.string.add_tx_date_label))

            Spacer(modifier = Modifier.height(8.dp))

            DateRow(
                dateText = state.dateText,
                onClick = { onAction(AddTransactionAction.OnToggleDatePicker) }
            )

            Spacer(modifier = Modifier.height(20.dp))

            FieldLabel(text = stringResource(R.string.add_tx_note_label))

            Spacer(modifier = Modifier.height(8.dp))

            CashTrackerTextField(
                value = state.note,
                onValueChange = { onAction(AddTransactionAction.OnNoteChange(it)) },
                placeholder = stringResource(R.string.add_tx_note_placeholder),
                singleLine = false
            )

            Spacer(modifier = Modifier.height(24.dp))

            CashTrackerPrimaryButton(
                text = stringResource(R.string.add_tx_save_button),
                onClick = { onAction(AddTransactionAction.OnSave) },
                enabled = !state.isSaving
            )
        }
    }
}

@Composable
private fun HeaderRow(
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.add_tx_title),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )
        IconButton(
            onClick = onClose,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        ) {
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = stringResource(R.string.add_tx_close_cd)
            )
        }
    }
}

@Composable
private fun AmountInput(
    amount: String,
    onAmountChange: (String) -> Unit,
    isExpense: Boolean,
    modifier: Modifier = Modifier
) {
    val textColor = if (isExpense && amount.isNotEmpty())
        MaterialTheme.colorScheme.error
    else
        MaterialTheme.colorScheme.onBackground

    val prefixTransformation = VisualTransformation { text ->
        TransformedText(
            buildAnnotatedString { append("$${text.text}") },
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int = offset + 1
                override fun transformedToOriginal(offset: Int): Int =
                    (offset - 1).coerceIn(0, text.length)
            }
        )
    }

    BasicTextField(
        value = amount,
        onValueChange = { newValue ->
            val filtered = newValue.filter { c -> c.isDigit() || c == '.' }
            if (filtered.count { it == '.' } <= 1) {
                onAmountChange(filtered)
            }
        },
        modifier = modifier.fillMaxWidth(),
        textStyle = MaterialTheme.typography.displaySmall.copy(
            color = textColor,
            textAlign = TextAlign.Center
        ),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        visualTransformation = if(amount.trim().isNotBlank())
            prefixTransformation
        else
            VisualTransformation.None,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (amount.isEmpty()) {
                    Text(
                        text = "$0.00",
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    )
                }
                innerTextField()
            }
        }
    )
}

@Composable
private fun FieldLabel(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text.uppercase(),
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.3.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier
    )
}

@Composable
private fun CategoryGrid(
    categories: List<CategoryUi>,
    selectedId: String?,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.chunked(3).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { category ->
                    CategoryCell(
                        category = category,
                        isSelected = category.id == selectedId,
                        onClick = { onSelect(category.id) },
                        modifier = Modifier.weight(1f)
                    )
                }
                if (row.size < 3) {
                    repeat(3 - row.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryCell(
    category: CategoryUi,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(category.color)
                .then(
                    if (isSelected) Modifier.border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(7.dp)
                    ) else Modifier
                )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = category.name,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun DateRow(
    dateText: String,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .clip(InputShape)
            .background(MaterialTheme.colorScheme.surface)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = dateText,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Icon(
            imageVector = Icons.Rounded.ArrowDropDown,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(locale = "ar")
@Composable
private fun AddTransactionScreenPreview() {
    CashTrackerTheme {
        AddTransactionScreen(
            state = AddTransactionState(
                categories = sampleCategories(TransactionType.Income)
            ),
            onAction = {}
        )
    }
}
