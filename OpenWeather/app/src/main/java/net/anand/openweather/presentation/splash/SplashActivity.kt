package net.anand.openweather.presentation.splash

import android.annotation.SuppressLint
import android.os.Handler
import net.anand.openweather.R
import net.anand.openweather.presentation.core.BaseActivity
import net.anand.openweather.presentation.utility.UiHelper
import org.koin.android.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    override fun getLayout(): Int {
        return R.layout.activity_splash
    }

    private val splashViewModel: SplashViewModel by viewModel()

    override fun getBaseViewModel() = splashViewModel

    override fun onResume() {
        super.onResume()

        Handler().postDelayed({
            UiHelper.startWeatherList(
                this@SplashActivity,
                true
            )

        }, 3000)
    }

}
