package net.anand.openweather.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Anand A <anandabktda@gmail.com>
 * The file mentioning base response structure
 * */

open class Response(
    @Expose
    @SerializedName("cod")
    var status: String? = null,
    @Expose
    @SerializedName("message")
    var message: String? = null,
    @Expose
    @SerializedName("cnt")
    var cnt: Int? = null
)

open class BaseResponse : Response() {
    val isSuccess: Boolean
        get() = status.equals("200")
}
