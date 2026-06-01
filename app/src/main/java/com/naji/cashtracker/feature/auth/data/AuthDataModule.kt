package com.naji.cashtracker.feature.auth.data

import com.naji.cashtracker.feature.auth.domain.AuthRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val authDataModule = module {
    singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
}
