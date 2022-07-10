package net.anand.openweather.data.repository

import net.anand.openweather.data.BaseRepository
import net.anand.openweather.data.Either
import net.anand.openweather.data.contract.CommonApisRepo
import net.anand.openweather.data.datasource.CommonApiLocalDataSource
import net.anand.openweather.data.datasource.CommonApisDataSource
import net.anand.openweather.data.models.WeatherListData
import net.anand.openweather.data.models.WeatherListRSP
import net.anand.openweather.domain.exceptions.MyException

class CommonApisRepository constructor(
    private val commonApisDataSource: CommonApisDataSource,
    private val commonApiLocalDataSource: CommonApiLocalDataSource
) : CommonApisRepo,
    BaseRepository() {

    override suspend fun getNewsListCall(latitude: Double,longitude: Double): Either<MyException, WeatherListRSP> {
        return either(commonApisDataSource.getNewsListCallAsync(latitude,longitude))
    }

    override suspend fun getNewsListSaved(): Either<MyException, List<WeatherListData>> {
        return eitherLocal(commonApiLocalDataSource.getNewsListSaved())
    }

    override suspend fun insertNewsList(weather: List<WeatherListData>): Either<MyException, List<WeatherListData>> {
        return eitherLocal(commonApiLocalDataSource.insertNewsList(weather))
    }

}