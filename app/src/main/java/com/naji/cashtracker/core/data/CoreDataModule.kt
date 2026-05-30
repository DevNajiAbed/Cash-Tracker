package com.naji.cashtracker.core.data

import org.koin.core.module.Module
import org.koin.dsl.module

val coreDataModule: Module = module {
    single { UserPreferences(get()) }
}
