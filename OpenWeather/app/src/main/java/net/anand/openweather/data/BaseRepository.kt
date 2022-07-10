package net.anand.openweather.data

import com.google.gson.JsonSyntaxException
import com.google.gson.stream.MalformedJsonException
import kotlinx.coroutines.Deferred
import net.anand.openweather.domain.exceptions.MyException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseRepository {

    suspend fun <R> either(service: Deferred<R>): Either<MyException, R> {
        return try {
            Either.Right(service.await())
        } catch (e: Exception) {
            if (e is HttpException) {
                if (e.code() == 422) {
                    Either.Left(transformException(e))
                } else {
                    e.printStackTrace()
                    Either.Left(transformException(e))
                }
            } else {
                e.printStackTrace()
                Either.Left(transformException(e))
            }
        }
    }

    fun <R> eitherLocal(service: R): Either<MyException, R> {
        return try {
            Either.Right(service)
        } catch (e: Exception) {
            e.printStackTrace()
            Either.Left(transformException(e))
        }
    }

    private fun transformException(e: Exception): MyException {
        if (e is HttpException) {
            when (e.code()) {
                422 -> return MyException.JsonException("Unable to parse api response", e)
                502 -> return MyException.JsonException("Unable to parse api response", e)
                500 -> return MyException.JsonException(
                    "Something went wrong",
                    e
                )
            }
        } else {
            if (e is ConnectException || e is UnknownHostException || e is SocketTimeoutException || e is SocketException) {
                return MyException.InternetConnectionException("No internet available", e)
            } else if (e is IllegalStateException || e is JsonSyntaxException || e is MalformedJsonException) {
                return MyException.JsonException("Unable to parse api response", e)
            }
        }
        return MyException.UnknownException(e)
    }

}