package net.anand.openweather.data.datasource

import net.anand.openweather.data.models.WeatherListData

interface CommonApiLocalDataSource {

    suspend fun getNewsListSaved(): List<WeatherListData>

    suspend fun insertNewsList(list: List<WeatherListData>): List<WeatherListData>

}