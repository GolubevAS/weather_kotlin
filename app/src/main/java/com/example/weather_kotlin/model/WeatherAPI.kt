package com.example.weather_kotlin.model

import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query



interface WeatherAPI {

    @GET("2.5/weather")
    fun getWeather(
        @Query("nameEn") nameEn: String,
        ): Call<WeatherDTO>
}

