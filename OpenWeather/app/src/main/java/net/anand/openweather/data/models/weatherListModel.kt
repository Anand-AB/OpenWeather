package net.anand.openweather.data.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by Anand A <anandabktda@gmail.com>
 * The model file contains News list params, also defined news table with additional param isSaved
 * */

class WeatherListRSP : BaseResponse() {
    val city: CityData? = null
    var list: List<WeatherListData>? = emptyList()
}

@Entity(tableName = "weather")
data class WeatherListData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") @SerializedName("id") val id: Long?,
    @ColumnInfo(name = "humidity") @SerializedName("humidity") val humidity: Int?,
    @Embedded val temp: TempData? = null
)

data class CityData(
    val name: String? = null
)

data class TempData(
    val min: Double? = null,
    var max: Double? = null
)