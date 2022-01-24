package com.example.weather_kotlin.model

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MainReceiver : BroadcastReceiver() {

    companion object {
        const val WEATHER_LOAD_SUCCESS = "WEATHER_LOAD_SUCCESS"
        const val WEATHER_LOAD_FAILED = "WEATHER_LOAD_FAILED"
    }

    override fun onReceive(context: Context, intent: Intent) {

        when (intent.action) {
            WEATHER_LOAD_SUCCESS -> RepositoryImpl.weatherLoader(intent.extras?.getParcelable("WEATHER_EXTRA"))
            WEATHER_LOAD_FAILED ->  RepositoryImpl.weatherLoader(null)
        }

    }
}