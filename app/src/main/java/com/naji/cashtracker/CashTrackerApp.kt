package com.naji.cashtracker

import android.app.Application
import com.naji.cashtracker.core.data.coreDataModule
import com.naji.cashtracker.feature.auth.data.authDataModule
import com.naji.cashtracker.feature.auth.presentation.authPresentationModule
import com.naji.cashtracker.feature.dashboard.presentation.dashboardPresentationModule
import com.naji.cashtracker.feature.onboarding.presentation.onboarding_screen.onboardingPresentationModule
import com.naji.cashtracker.feature.transaction.presentation.transactionPresentationModule
import com.naji.cashtracker.feature.splash.presentation.splash_screen.splashPresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CashTrackerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CashTrackerApp)
            modules(
                coreDataModule,
                splashPresentationModule,
                onboardingPresentationModule,
                authDataModule,
                authPresentationModule,
                dashboardPresentationModule,
                transactionPresentationModule,
            )
        }
    }
}
