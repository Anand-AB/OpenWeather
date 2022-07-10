package net.anand.openweather.data.contract

import net.anand.openweather.data.Either
import net.anand.openweather.data.models.WeatherListData
import net.anand.openweather.data.models.WeatherListRSP
import net.anand.openweather.domain.exceptions.MyException

interface CommonApisRepo {

    suspend fun getNewsListCall(latitude: Double,longitude: Double): Either<MyException, WeatherListRSP>

    suspend fun getNewsListSaved(): Either<MyException, List<WeatherListData>>

    suspend fun insertNewsList(news: List<WeatherListData>): Either<MyException, List<WeatherListData>>

}