package com.naji.cashtracker.feature.dashboard.presentation

import com.naji.cashtracker.feature.dashboard.presentation.home.HomeViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val dashboardPresentationModule: Module = module {
    viewModelOf(::HomeViewModel)
}
