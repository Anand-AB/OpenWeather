package net.anand.openweather.di

import net.anand.openweather.data.datasource.CommonApiLocalDataSource
import net.anand.openweather.data.datasource.CommonApisDataSource
import net.anand.openweather.data.ds.CommonApiLocalDS
import net.anand.openweather.data.ds.CommonApisRemoteDS
import org.koin.dsl.module.module

val dataSourceModule = module {
    single<CommonApisDataSource> { CommonApisRemoteDS(get()) }
    single<CommonApiLocalDataSource> { CommonApiLocalDS(get()) }
}