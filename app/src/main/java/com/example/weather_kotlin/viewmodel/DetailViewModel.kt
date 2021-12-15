package com.example.weather_kotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather_kotlin.model.Repository
import com.example.weather_kotlin.model.RepositoryImpl
import com.example.weather_kotlin.model.Weather
import kotlin.random.Random

class DetailViewModel : ViewModel() {

    private val liveDataToObserve : MutableLiveData<AppState> = MutableLiveData()
    private val repo : Repository = RepositoryImpl()

    fun getData() : LiveData<AppState> = liveDataToObserve

    fun getWeather () {
        liveDataToObserve.value = AppState.Loading

        
        // тут будем прикручивать поход в интернет
        // необходим Репозиторий - от куда будет эти данные получать
        Thread {
            
            Thread.sleep(1000)

            if (Random.nextBoolean()) {
                val weather = repo.getWeatherFromServer()
                liveDataToObserve.postValue(AppState.Success(weather))
            } else {
                liveDataToObserve.postValue(AppState.Error(Exception("Проверьте интеренет")))
            }

        }.start()
    }


}