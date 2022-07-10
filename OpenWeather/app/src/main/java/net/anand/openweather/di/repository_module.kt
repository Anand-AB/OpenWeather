package net.anand.openweather.di

import net.anand.openweather.data.contract.CommonApisRepo
import net.anand.openweather.data.repository.CommonApisRepository
import org.koin.dsl.module.module

val repositoryModule = module {
    single<CommonApisRepo> { CommonApisRepository(get(), get()) }
}