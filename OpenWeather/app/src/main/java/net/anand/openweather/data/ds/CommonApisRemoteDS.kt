package net.anand.openweather.data.ds

import kotlinx.coroutines.Deferred
import net.anand.openweather.data.datasource.CommonApisDataSource
import net.anand.openweather.data.models.WeatherListRSP
import net.anand.openweather.domain.network.CommonApiService

class CommonApisRemoteDS constructor(private val commonApiService: CommonApiService) :
    CommonApisDataSource {

    override suspend fun getNewsListCallAsync(
        latitude: Double,
        longitude: Double
    ): Deferred<WeatherListRSP> {
        return commonApiService.getNewsListCallAsync(latitude, longitude)
    }
}