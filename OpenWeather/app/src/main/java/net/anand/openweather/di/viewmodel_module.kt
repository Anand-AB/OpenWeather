package net.anand.openweather.di

import net.anand.openweather.presentation.splash.SplashViewModel
import net.anand.openweather.presentation.weatherList.WeatherListViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { WeatherListViewModel(get()) }
}
