package net.anand.openweather.domain.network

import kotlinx.coroutines.Deferred
import net.anand.openweather.BuildConfig
import net.anand.openweather.data.models.WeatherListRSP
import net.anand.openweather.presentation.utility.AppConstants.Companion.API_DAILY_WEATHER
import retrofit2.http.GET
import retrofit2.http.Query

interface CommonApiService {

    @GET(API_DAILY_WEATHER)
    fun getNewsListCallAsync(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String = BuildConfig.API_KEY
    ): Deferred<WeatherListRSP>

}