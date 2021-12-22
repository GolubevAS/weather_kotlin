package com.example.weather_kotlin.model

import com.google.gson.annotations.SerializedName

data class WeatherDTO(

    val main: MainDTO?

)

data class MainDTO(
    val temp: Int?,
    @SerializedName("feels_like")
    val feelsLike: Int?,
    @SerializedName("temp_min")
    val tempMin: Int?,
    @SerializedName("temp_max")
    val tempMax: Int?,
    val pressure: Int?,
    val humidity: Int?,

    val icon: String?,
    val description: String?
)



