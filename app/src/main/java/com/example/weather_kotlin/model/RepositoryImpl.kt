package com.example.weather_kotlin.model

object RepositoryImpl : Repository {

    //подписчики, которые будут ждат сообщения
    private val listeners : MutableList<Repository.OnLoadListener> = mutableListOf()
    private var weather : Weather? = null

    override fun getWeatherFromServer(): Weather? = weather

    override fun getWeatherFromLocalStorageRus(): List<Weather> = getRussianCities()

    override fun getWeatherFromLocalStorageWorld(): List<Weather> = getWorldCity()

    override fun weatherLoader (weather: Weather?) {
        this.weather = weather
        listeners.forEach{it.onLoaded()}
    }

    override fun addLoadListener(listener: Repository.OnLoadListener) {
        listeners.add(listener)
    }

    override fun removeLoadListener(listener: Repository.OnLoadListener) {
        listeners.remove(listener)
    }


}