package net.anand.openweather.data.ds

import net.anand.openweather.data.datasource.CommonApisDataSource
import net.anand.openweather.data.models.WeatherListRSP
import net.anand.openweather.domain.network.CommonApiService
import kotlinx.coroutines.Deferred
import net.anand.openweather.BuildConfig

class CommonApisRemoteDS constructor(private val commonApiService: CommonApiService) :
    CommonApisDataSource {

    override suspend fun getNewsListCallAsync(latitude: Double,longitude: Double): Deferred<WeatherListRSP> {
        return commonApiService.getNewsListCallAsync(latitude,longitude)
    }
}