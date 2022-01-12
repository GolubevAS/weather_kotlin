package com.example.weather_kotlin.model

import com.google.gson.annotations.SerializedName

data class WeatherDTO(

    val main: MainDTO?

)

data class MainDTO(
    val temp: Float?,
    @SerializedName("feels_like")
    val feelsLike: Float?,
    @SerializedName("temp_min")
    val tempMin: Float?,
    @SerializedName("temp_max")
    val tempMax: Float?,
    val pressure: Float?,
    val humidity: Float?,

    val icon: String?,
    val description: String?
)



