package net.anand.openweather.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.weather_row_layout.view.*
import net.anand.openweather.R
import net.anand.openweather.data.models.WeatherListData

/**
 * Created by Anand A <anandabktda@gmail.com>
 * Weather list Adapter
 * */

open class WeatherListAdapter
    (val context: Context?) : RecyclerView.Adapter<WeatherListAdapter.ViewHolder>() {

    var weatherList: MutableList<WeatherListData> = arrayListOf()

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view =
            LayoutInflater.from(p0.context).inflate(R.layout.weather_row_layout, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        holder.bind(weatherList[p1])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                itemOnCLick(weatherList[adapterPosition])
            }
        }

        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bind(data: WeatherListData) {

            try {
                context?.let { context ->
                    itemView.tv_min_weather.text =
                        "${context.getString(R.string.label_min_weather)} ${data.temp!!.min}"
                    itemView.tv_max_weather.text =
                        "${context.getString(R.string.label_max_weather)} ${data.temp.max}"
                    itemView.tv_humidity.text =
                        "${context.getString(R.string.label_humidity)} ${data.humidity}"
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    open fun itemOnCLick(data: WeatherListData) {}

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(newsList: List<WeatherListData>) {
        this.weatherList.addAll(newsList)
        notifyDataSetChanged()
    }

    fun clear() {
        this.weatherList.clear()
    }
}