package com.example.weather_kotlin.model

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi


class MainIntentService : IntentService("MainIntentService") {

    companion object {
        const val TAG = "MainIntentService"
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onHandleIntent(intent: Intent?) {
        Log.d(WeatherService.TAG, "current thread " + Thread.currentThread().name)

        Thread.sleep(5000)

        intent?.getParcelableExtra<Weather>("WEATHER_EXTRA")?.let { weather ->
            WeatherLoader.loadRetrofit(weather.city, object : WeatherLoader.OnWeatherLoadListener {
                override fun onLoaded(weatherDTO: WeatherDTO) {
                    applicationContext.sendBroadcast(Intent(
                        applicationContext,
                        MainReceiver::class.java
                    )
                        .apply
                        {
                            action = MainReceiver.WEATHER_LOAD_SUCCESS
                            putExtra(
                                "WEATHER_EXTRA", Weather(
                                    temperature = weatherDTO.main?.temp ?: 0.00,
                                    feelsLike = weatherDTO.main?.feelsLike ?: 0.00,
                                    description = weatherDTO.main?.description ?: "",
                                )
                            )
                        })
                }

                override fun onFailed(throwable: Throwable) {
                    applicationContext.sendBroadcast(Intent(
                        applicationContext,
                        MainReceiver::class.java
                    )
                        .apply {
                            action = MainReceiver.WEATHER_LOAD_FAILED
                        })
                }

            })
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(WeatherService.TAG, "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(WeatherService.TAG, "onStartCommand $intent")
        return super.onStartCommand(intent, flags, startId)

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(WeatherService.TAG, "onDestroy")
    }
}



