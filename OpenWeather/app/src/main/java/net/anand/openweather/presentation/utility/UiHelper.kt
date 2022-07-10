package net.anand.openweather.presentation.utility

import android.app.Activity
import android.content.Intent
import net.anand.openweather.R
import net.anand.openweather.presentation.core.BaseActivity
import net.anand.openweather.presentation.weatherList.WeatherListActivity

class UiHelper {

    companion object {

        internal fun startWeatherList(context: BaseActivity, clearTop: Boolean? = false) {
            val intent = Intent(context, WeatherListActivity::class.java)
            if (clearTop!!) {
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            context.startActivity(intent)
            animateTransition(context)
        }

        private fun animateTransition(activity: Activity) {
            activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

    }
}