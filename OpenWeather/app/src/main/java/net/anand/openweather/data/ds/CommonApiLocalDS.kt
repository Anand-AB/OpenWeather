package net.anand.openweather.data.ds

import net.anand.openweather.data.BaseRepository
import net.anand.openweather.data.database.OpenWeatherDao
import net.anand.openweather.data.datasource.CommonApiLocalDataSource
import net.anand.openweather.data.models.WeatherListData

class CommonApiLocalDS constructor(private val newsBreezeDao: OpenWeatherDao) : BaseRepository(),
    CommonApiLocalDataSource {

    override suspend fun getNewsListSaved(): List<WeatherListData> {
        return newsBreezeDao.getWeatherListSaved()
    }

    override suspend fun insertNewsList(list: List<WeatherListData>): List<WeatherListData> {
        newsBreezeDao.deleteSavedWeatherList()
        newsBreezeDao.insertWeatherList(list)
        return newsBreezeDao.getWeatherListSaved()
    }

}