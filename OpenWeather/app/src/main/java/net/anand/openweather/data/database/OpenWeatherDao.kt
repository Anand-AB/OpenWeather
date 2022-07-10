package net.anand.openweather.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import net.anand.openweather.data.models.WeatherListData

/**
 * Created by Anand A <anandabktda@gmail.com>
 * The Dao helps to do data base operations
 * */

@Dao
interface OpenWeatherDao {

    @androidx.room.Query("SELECT * FROM weather ")
    fun getWeatherListSaved(): List<WeatherListData>

    @androidx.room.Query("DELETE FROM weather")
    fun deleteSavedWeatherList()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertWeatherList(list: List<WeatherListData>)

}