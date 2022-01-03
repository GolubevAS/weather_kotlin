package com.example.weather_kotlin.model

import com.google.gson.annotations.SerializedName

data class WeatherDTO(

    val main: MainDTO?

)

data class MainDTO(
    val temp: Double?,
    @SerializedName("feels_like")
    val feelsLike: Double?,
    @SerializedName("temp_min")
    val tempMin: Double?,
    @SerializedName("temp_max")
    val tempMax: Double?,
    val pressure: Int?,
    val humidity: Int?,

    val icon: String?,
    val description: String?
)



