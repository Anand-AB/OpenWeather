package net.anand.openweather.domain.exceptions

/**
 * Created by Anand A <anandabktda@gmail.com>
 * For Exception Handling
 * */

sealed class MyException(t: Throwable) : Exception() {
    class InternetConnectionException(msg: String, e: Exception) : MyException(e)
    class UnknownException(e: Exception) : MyException(e)
    class JsonException(msg: String, e: Exception) : MyException(e)
}