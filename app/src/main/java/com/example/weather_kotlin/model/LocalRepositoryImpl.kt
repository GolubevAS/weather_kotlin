package com.example.weather_kotlin.model

import java.util.*

class LocalRepositoryImpl(private val dao: HistoryDAO) : LocalRepository {

    override fun getAllHistory(): List<Weather> {
        return dao.all()
            .map { entity ->
                Weather(
                    city = City(entity.city),
                    temperature = entity.temperature
                )
            }
    }

    override fun saveEntity(weather: Weather) {
        dao.insert(
            HistoryEntity(
                id = 0,
                city = weather.city.name,
                temperature = weather.temperature,
                timestamp = Date().time
            )
        )
    }

}
