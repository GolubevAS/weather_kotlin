package com.example.weather_kotlin.viewmodel

import com.example.weather_kotlin.model.Weather

sealed class AppState {

    // три состояния view (нашего экрана)
    data class Success(val weather : Weather) : AppState() //класс состояния

    data class Error(val error : Throwable) : AppState() //класс состояния

    object Loading : AppState() //класс состояния



}
