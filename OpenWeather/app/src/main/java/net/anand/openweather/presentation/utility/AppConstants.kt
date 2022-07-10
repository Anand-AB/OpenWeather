package net.anand.openweather.presentation.utility

interface AppConstants {
    companion object {

        ////////////////////////////////////////// Local Store Constants /////////////////////////////////////////////////
        const val DB_NAME = "OpenWeatherDatabase"
        const val GPS_REQUEST = 1001
        const val LOCATION_REQUEST = 1000

        ////////////////////////////////////////// API Constants /////////////////////////////////////////////////
        const val BASE_URL = "http://api.openweathermap.org/"
        const val API_DAILY_WEATHER = "data/2.5/forecast/daily"

        ////////////////////////////////////////// commonly using values //////////////////////////////////////////
        const val CHIVO_REGULAR_TTF = "chivo_regular.ttf"
        const val QUEENS_PARK_TTF = "queens_park_bold.ttf"
        const val QUEENS_PARK_NORMAL_TTF = "queens_park.ttf"
    }
}