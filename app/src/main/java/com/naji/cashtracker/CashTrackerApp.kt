package com.naji.cashtracker

import android.app.Application
import com.naji.cashtracker.core.data.coreDataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CashTrackerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CashTrackerApp)
            modules(
                coreDataModule,
            )
        }
    }
}
