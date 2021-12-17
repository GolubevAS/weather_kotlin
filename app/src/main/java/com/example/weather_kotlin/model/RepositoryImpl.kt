package com.example.weather_kotlin.model

class RepositoryImpl : Repository {

    override fun getWeatherFromServer(): Weather = Weather()

    override fun getWeatherFromLocalStorageRus(): List<Weather> = getRussianCities()

    override fun getWeatherFromLocalStorageWorld(): List<Weather> = getWorldCity()



}