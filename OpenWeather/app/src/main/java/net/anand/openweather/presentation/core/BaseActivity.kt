package net.anand.openweather.presentation.core

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import net.anand.openweather.R
import net.anand.openweather.domain.exceptions.MyException
import net.anand.openweather.presentation.core.views.CustomProgressDialog
import net.anand.openweather.presentation.core.views.CustomProgressWithMessageDialog
import net.anand.openweather.presentation.utility.showDialog
import kotlin.coroutines.CoroutineContext

/**
 * Created by Anand A <anandabktda@gmail.com>
 * */

abstract class BaseActivity : AppCompatActivity(), CoroutineScope {

    lateinit var job: Job
    private var progress: CustomProgressDialog? = null
    private var progressMessage: CustomProgressWithMessageDialog? = null
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    abstract fun getBaseViewModel(): BaseViewModel?
    abstract fun getLayout(): Int

    companion object {
        var dialogShowing = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()

        progress =
            CustomProgressDialog(
                this
            )
        progressMessage =
            CustomProgressWithMessageDialog(
                this
            )

        setContentView(getLayout())
        initiation()
        setOnClickListener()
        getApiResponse()

        getBaseViewModel()?.exceptionLiveData?.observe(this, Observer {
            hideProgress()

            it?.apply {
                when (this) {
                    is MyException.InternetConnectionException -> {
                        showDialog(
                            msg = getString(R.string.error_network),
                            listener = DialogInterface.OnClickListener { dialog, _ ->
                                dialog.dismiss()
                            })
                    }
                    is MyException.JsonException -> {

                        showDialog(
                            msg = "Unparceble data found from server",
                            listener = DialogInterface.OnClickListener { dialog, _ ->
                                dialog.dismiss()
                            })
                    }
                    else -> {
                        var message = it.message
                        message = if (message?.isEmpty() == true) {
                            "Unknown error : " + it.localizedMessage
                        } else
                            "Something went wrong"
                        showDialog(
                            msg = message,
                            listener = DialogInterface.OnClickListener { dialog, _ ->
                                dialog.dismiss()
                            })
                    }
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    fun showProgress() {
        if (!this.isFinishing) {
            progress?.show()
        }
    }

    fun hideProgress() {
        if (!this.isFinishing && progress?.isShowing == true) {
            progress?.dismiss()
        }
    }

    open fun setOnClickListener() {}
    open fun initiation() {}
    open fun getApiResponse() {}
}