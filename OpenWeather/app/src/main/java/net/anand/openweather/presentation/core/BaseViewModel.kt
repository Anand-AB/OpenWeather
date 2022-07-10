package net.anand.openweather.presentation.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import net.anand.openweather.data.Either
import net.anand.openweather.domain.exceptions.MyException
import kotlin.coroutines.CoroutineContext

/**
 * Created by Anand A <anandabktda@gmail.com>
 * */

open class BaseViewModel : ViewModel(), CoroutineScope {

    val exceptionLiveData: MutableLiveData<Exception> = MutableLiveData()

    var job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun <R> postValue(either: Either<MyException, R>, successLiveData: MutableLiveData<R>) {

        either.either({
            exceptionLiveData.postValue(it)
        }, {
            successLiveData.postValue(it)
        })

    }
}