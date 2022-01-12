package com.example.weather_kotlin.model

interface LocalRepository {

    fun getAllHistory(): List<Weather>
    fun saveEntity(weather: Weather)

}