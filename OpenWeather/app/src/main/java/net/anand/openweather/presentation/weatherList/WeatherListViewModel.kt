package net.anand.openweather.presentation.weatherList

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import net.anand.openweather.data.contract.CommonApisRepo
import net.anand.openweather.data.models.WeatherListData
import net.anand.openweather.data.models.WeatherListRSP
import net.anand.openweather.presentation.core.BaseViewModel
import net.anand.openweather.presentation.utility.Helper.Companion.getCurrentFormatedTime

/**
 * Created by Anand A <anandabktda@gmail.com>
 * The ViewModel used for process weather list data
 * */

class WeatherListViewModel constructor(private val commonApisRepo: CommonApisRepo) :
    BaseViewModel() {

    private val weatherListLiveData: MutableLiveData<WeatherListRSP> = MutableLiveData()
    private val isShowProgressLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val weatherListSavedLiveData: MutableLiveData<List<WeatherListData>> = MutableLiveData()
    private val cityLiveData: MutableLiveData<String> = MutableLiveData()
    private val updatedTimeLiveData: MutableLiveData<String> = MutableLiveData()

    internal fun init(latitude: Double = 0.0, longitude: Double = 0.0) {
        weatherListLiveData.value?.let { weatherData ->
            weatherData.list?.let {
                return
            }
        }

        if (latitude != 0.0 && longitude != 0.0) {
            getNewsListCall(latitude, longitude)
        } else {
            getNewsListSaved()
        }

    }

    private fun getNewsListCall(latitude: Double, longitude: Double) {
        launch {
            postValue(
                commonApisRepo.getNewsListCall(latitude, longitude),
                weatherListLiveData
            )
        }
    }

    // progress status live data
    internal fun getShowProgressLiveData(): LiveData<Boolean> {
        return isShowProgressLiveData
    }

    // weather live data
    internal fun weatherListLiveData(): LiveData<WeatherListRSP> {
        return weatherListLiveData
    }

    // saved weather live data
    internal fun weatherListSavedLiveData(): LiveData<List<WeatherListData>> {
        return weatherListSavedLiveData
    }

    private fun getNewsListSaved() {
        launch {
            postValue(commonApisRepo.getNewsListSaved(), weatherListSavedLiveData)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    internal fun insertNewsList(weatherData: WeatherListRSP) {
        val currentFormatTime = getCurrentFormatedTime()
        updatedTimeLiveData.postValue(currentFormatTime)

        weatherData.city?.let { cityData ->
            cityLiveData.postValue(cityData.name)
        }

        weatherData.list?.let { weatherList ->
            launch {
                postValue(commonApisRepo.insertNewsList(weatherList), weatherListSavedLiveData)
            }
        }

    }

    // saved city live data
    internal fun cityLiveData(): LiveData<String> {
        return cityLiveData
    }

    // saved updated time live data
    internal fun updatedTimeLiveData(): LiveData<String> {
        return updatedTimeLiveData
    }

}