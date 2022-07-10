package net.anand.openweather.di

import androidx.room.Room
import net.anand.openweather.data.database.MyAppDataBase
import net.anand.openweather.presentation.utility.AppConstants.Companion.DB_NAME
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

val roomModule = module {

    single {
        Room.databaseBuilder(androidApplication(), MyAppDataBase::class.java, DB_NAME).build()
    }

    single { get<MyAppDataBase>().getDhCoreDao() }
}