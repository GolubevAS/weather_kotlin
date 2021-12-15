package com.example.weather_kotlin.viewmodel


sealed class AppState {

    // три состояния view (нашего экрана)
    data class Success<T>(val data : T) : AppState()     //класс состояния

    data class Error(val error : Throwable) : AppState() //класс состояния

    object Loading : AppState()                         //класс состояния



}
