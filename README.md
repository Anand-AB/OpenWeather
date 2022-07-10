# Open Weather
Developed using Kotlin, MVVM, Retrofit, Shared Preference, Room, koin, Recyclearview

# Features
Splash Screen : 
* The entryscreen will visible 3seconds. After willmove to weather list

Weather List : 
* The list will show show based on internet connectivity. If the internet is available weather list taking from http://api.openweathermap.org/, after fetching the list it will store in local db
* If internet not there check local data base, if the list contains will show in list
* Using WeatherListViewModel as view model. Here will get action request from WeatherListActivity. View model will get current latitude and longitude if we are using internet. Will take weather list using latlong. If latlong is empty will return local stored list.
* After getting weather list will replace the local db data. Also saving city name and updated time in  shared preference
* Koin is using for dependency injection
* Courotine - Retrofit handle API Request processing

