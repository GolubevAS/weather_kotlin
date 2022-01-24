package com.example.weather_kotlin.model

interface Repository {

    fun getWeatherFromServer(): Weather?

    fun getWeatherFromLocalStorageRus(): List<Weather>

    fun getWeatherFromLocalStorageWorld(): List<Weather>

    fun weatherLoader(weather: Weather?)

    fun addLoadListener(listener: OnLoadListener)
    fun removeLoadListener(listener: OnLoadListener)

   fun interface OnLoadListener {
        fun onLoaded()
    }



}