package com.naji.cashtracker.feature.transaction.presentation

import com.naji.cashtracker.feature.transaction.presentation.addedit.AddTransactionViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val transactionPresentationModule: Module = module {
    viewModelOf(::AddTransactionViewModel)
}
