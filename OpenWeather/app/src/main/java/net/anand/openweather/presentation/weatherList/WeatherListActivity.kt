package net.anand.openweather.presentation.weatherList

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_news_list.*
import kotlinx.android.synthetic.main.layout_toolbar_main.*
import net.anand.openweather.MyApplication
import net.anand.openweather.R
import net.anand.openweather.data.models.WeatherListData
import net.anand.openweather.presentation.adapter.WeatherListAdapter
import net.anand.openweather.presentation.core.BaseActivity
import net.anand.openweather.presentation.utility.AppConstants
import net.anand.openweather.presentation.utility.AppConstants.Companion.LOCATION_REQUEST
import net.anand.openweather.presentation.utility.GpsUtils
import net.anand.openweather.presentation.utility.isNetworkConnected
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by Anand A <anandabktda@gmail.com>
 * The Activity used for showing weather list
 * */

class WeatherListActivity : BaseActivity() {

    private var newsListAdapter: WeatherListAdapter? = null
    private var newsList: List<WeatherListData> = arrayListOf()
    private var location: Location? = null
    private var locationManager: LocationManager? = null
    private var locationRequest: LocationRequest? = null
    private var locationCallback: LocationCallback? = null
    private var mFusedLocationClient: FusedLocationProviderClient? = null

    override fun getLayout(): Int {
        return R.layout.activity_news_list
    }

    private val weatherListViewModel: WeatherListViewModel by viewModel()
    override fun getBaseViewModel() = weatherListViewModel

    override fun initiation() {
        super.initiation()

        setSupportActionBar(toolbar)
        setAdapter()

        weatherListViewModel.init()
        if (isNetworkConnected()) {
            handleLocation()
        }

    }

    private fun setAdapter() {
        newsListAdapter = object : WeatherListAdapter(this) {

        }
        rv_news.adapter = newsListAdapter
    }

    // GPS status result checking
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppConstants.GPS_REQUEST) {
            if (resultCode == RESULT_OK) {
                getLocation()
            }
        }
    }

    // Handle current location, check GPS status
    private fun handleLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationRequest = LocationRequest.create()
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        GpsUtils(this@WeatherListActivity).turnGPSOn {
            // turn on GPS
            getLocation()
        }

        // based on location call back setting current location and request for weather list
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {

                    if (this@WeatherListActivity.location == null) {
                        this@WeatherListActivity.location = location
                        weatherListViewModel.init(location.latitude, location.longitude)
                        mFusedLocationClient?.removeLocationUpdates(locationCallback)
                    }

                }
            }
        }
    }

    // Checking the location permission, if it is enable request for current location,
    // otherwise handle the permission popup
    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this@WeatherListActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                this@WeatherListActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@WeatherListActivity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_REQUEST
            )
        } else {
            mFusedLocationClient?.lastLocation
                ?.addOnSuccessListener(this@WeatherListActivity) { location ->
                    if (location != null) {
                        if (location.latitude != 0.0 && location.longitude != 0.0) {
                            if (this@WeatherListActivity.location == null) {
                                this@WeatherListActivity.location = location
                                weatherListViewModel.init(location.latitude, location.longitude)
                                mFusedLocationClient?.removeLocationUpdates(locationCallback)
                            }
                        }
                    } else {
                        locationRequest?.let { locationRequest ->
                            if (locationCallback != null) {
                                mFusedLocationClient?.requestLocationUpdates(
                                    locationRequest,
                                    locationCallback,
                                    null
                                )
                            }
                        }
                    }
                }
        }
    }

    // Permission result will get
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }

                mFusedLocationClient?.let { fusedLocationProviderClient ->
                    fusedLocationProviderClient.lastLocation.addOnSuccessListener(this@WeatherListActivity) { location ->
                        showProgress()
                        if (location != null) {
                            if (location.latitude != 0.0 && location.longitude != 0.0) {
                                if (this@WeatherListActivity.location == null) {
                                    this@WeatherListActivity.location = location
                                    weatherListViewModel.init(location.latitude, location.longitude)
                                    fusedLocationProviderClient.removeLocationUpdates(
                                        locationCallback)
                                }
                            }

                        } else {
                            fusedLocationProviderClient.requestLocationUpdates(
                                locationRequest,
                                locationCallback,
                                null
                            )
                        }
                    }
                }

            } else {
                Toast.makeText(this,
                    getString(R.string.error_permission_denied),
                    Toast.LENGTH_SHORT).show()
                weatherListViewModel.init()
            }
        }
    }

    // Passing weather data to adapter
    private fun setWeatherData(newsList: List<WeatherListData>) {
        hideProgress()
        newsListAdapter?.clear()
        newsListAdapter?.addAll(newsList)

        rv_news.visibility = View.VISIBLE
        tv_no_data.visibility = View.GONE
    }

    // Handling weather list empty case
    private fun weatherListEmpty(message: String) {
        hideProgress()
        rv_news.visibility = View.GONE
        tv_no_data.visibility = View.VISIBLE
        tv_no_data.text = message
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getApiResponse() {
        super.getApiResponse()

        // Handle progress dialog
        weatherListViewModel.getShowProgressLiveData().observe(this, Observer { isShowProgress ->
            if (isShowProgress) {
                showProgress()
            } else {
                hideProgress()
            }
        })

        // Handle weather data from API, store to local db
        weatherListViewModel.weatherListLiveData().observe(this, Observer {
            if (it.isSuccess) {
                it.list?.let { weatherList ->
                    if (weatherList.isNotEmpty()) {
                        weatherListViewModel.insertNewsList(it)
                    }
                }
            } else {
                it.message?.let { message ->
                    weatherListEmpty(message)
                }
            }
        })

        // Handle saved weather data
        weatherListViewModel.weatherListSavedLiveData().observe(this, Observer {
            tv_city_name.text = MyApplication.getCity()
            tv_last_update.text = MyApplication.getLastUpdated()

            if (it.isNotEmpty()) {
                newsList = it
                setWeatherData(it)
            } else {
                weatherListEmpty(getString(R.string.text_no_data))
            }
        })

        // Save city name
        weatherListViewModel.cityLiveData().observe(this, Observer { cityName ->
            MyApplication.setCity(cityName)
        })

        // Save last updated time
        weatherListViewModel.updatedTimeLiveData().observe(this, Observer { lastUpdated ->
            MyApplication.setLastUpdated(lastUpdated)
        })

    }

}
