package net.anand.openweather.presentation.utility

import android.content.Context
import android.content.SharedPreferences
import net.anand.openweather.MyApplication
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class Helper {
    companion object {
        private var sharedpreferences: SharedPreferences? = null
        private const val PREFERENCE = "pref_open_weather"

        fun setStringValue(key: String, value: String) {
            sharedpreferences = MyApplication.getAppInstance()
                ?.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            val editor = sharedpreferences?.edit()
            editor?.putString(key, value)
            editor?.apply()
        }

        /**
         * Get string value from shared preferences
         *
         * @param key key of value
         * @return value of the key
         */
        fun getStringValue(key: String): String? {
            sharedpreferences = MyApplication.getAppInstance()
                ?.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            return sharedpreferences?.getString(key, "")
        }

        internal fun getCurrentFormatedTime(): String? {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            return current.format(formatter)
        }

    }
}