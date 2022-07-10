package net.anand.openweather.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import net.anand.openweather.data.models.WeatherListData

/**
 * Created by Anand A <anandabktda@gmail.com>
 * The file used for database details setup
 * */

@Database(
    entities = [WeatherListData::class
    ], version = 1, exportSchema = false
)

//@TypeConverters(
//    Converters::class
//)
abstract class MyAppDataBase : RoomDatabase() {

    abstract fun getDhCoreDao(): OpenWeatherDao
}