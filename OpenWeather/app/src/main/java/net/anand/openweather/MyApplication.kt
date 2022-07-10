package net.anand.openweather

import android.app.Application
import net.anand.openweather.di.*
import net.anand.openweather.domain.network.CommonApiService
import net.anand.openweather.presentation.utility.AppConstants.Companion.BASE_URL
import net.anand.openweather.presentation.utility.Helper.Companion.getStringValue
import net.anand.openweather.presentation.utility.Helper.Companion.setStringValue
import net.anand.openweather.presentation.utility.PrefKeys
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.android.ext.android.startKoin
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        mAppInstance = this
        init()
    }

    private fun init() {
        startKoin(
            this, arrayListOf(
                networkModule,
                repositoryModule,
                dataSourceModule,
                viewModelModule,
                roomModule
            )
        )

        val httpLoggingInterceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        // We need to prepare a custom OkHttp client because need to use our custom call interceptor.
        // to be able to authenticate our requests.
        val builder = OkHttpClient.Builder()
            .connectTimeout(90L, TimeUnit.SECONDS)
            .readTimeout(90L, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .cookieJar(MyCookieJar())
        // We add the interceptor to OkHttpClient.
        // It will add authentication headers to every call we make.
//        builder.interceptors().add(AuthenticationInterceptor())
        val client = builder.build()

        apiErrorHandler = Retrofit.Builder() // Create retrofit builder.
            .baseUrl(BASE_URL) // Base url for the api has to end with a slash.
            .addConverterFactory(GsonConverterFactory.create()) // Use GSON converter for JSON to POJO object mapping.
            .client(client) // Here we set the custom OkHttp client we just created.
            .build()
            .create(CommonApiService::class.java) // We create an API using the interface we defined.

    }

    companion object {
        var apiErrorHandler: CommonApiService? = null
        private var mAppInstance: MyApplication? = null

        fun getAppInstance(): MyApplication? {
            return mAppInstance
        }

        internal fun setCity(city: String?) {
            city?.let { cityGot ->
                setStringValue(PrefKeys.City, cityGot)
            }
        }

        internal fun getCity(): String? {
            return getStringValue(PrefKeys.City)
        }

        internal fun setLastUpdated(lastUpdated: String?) {
            lastUpdated?.let { lastUpdatedGot ->
                setStringValue(PrefKeys.LastUpdated, lastUpdatedGot)
            }
        }

        internal fun getLastUpdated(): String? {
            return getStringValue(PrefKeys.LastUpdated)
        }

    }
}