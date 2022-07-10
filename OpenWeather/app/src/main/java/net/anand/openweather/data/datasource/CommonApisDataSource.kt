package net.anand.openweather.data.datasource

import kotlinx.coroutines.Deferred
import net.anand.openweather.data.models.WeatherListRSP

interface CommonApisDataSource {

    suspend fun getNewsListCallAsync(latitude: Double,longitude: Double): Deferred<WeatherListRSP>

}