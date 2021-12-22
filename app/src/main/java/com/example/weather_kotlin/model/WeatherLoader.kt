package com.example.weather_kotlin.model

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.stream.Collectors

object WeatherLoader {

    private const val YOUR_API_KEY = "2464e2e4321ac072c7a8011bf47da68e"

    @RequiresApi(Build.VERSION_CODES.N)
    fun load(city: City, listener: OnWeatherLoadListener) {

        val handler = Handler(Looper.getMainLooper())

        //  выход в интренет всегда создаем в отдельном потоке
        Thread {
            var urlConnection: HttpURLConnection? = null
            val handler = Handler(Looper.getMainLooper())

            try {


                val uri = URL("https://api.openweathermap.org/data/2.5/weather?q=${city.nameEn}&appid=${YOUR_API_KEY}&units=metric&lang=ru")


                // настраиваем соединение с Интернетом
                urlConnection = uri.openConnection() as HttpURLConnection
                //urlConnection.addRequestProperty("2464e2e4321ac072c7a8011bf47da68e", YOUR_API_KEY )
                urlConnection.requestMethod = "GET"
                urlConnection.readTimeout = 1000
                urlConnection.connectTimeout = 1000

                // как будем считывать данные
                val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val result = reader.lines().collect(Collectors.joining("\n"))

                //эта строка преобразует наш JSON в WeatherDTO
                val weatherDTO = Gson().fromJson(result, WeatherDTO::class.java)

                // в каком потоке мы вызвали в тот мы и отправляем инфо
                handler.post {
                    listener.onLoaded(weatherDTO)
                }

            } catch (e: Exception) {
                handler.post {
                    listener.onFailed(e)
                }
                Log.e("DEBUGLOG", "FAIL CONNECTION")
            } finally {
                urlConnection?.disconnect()
            }
        }.start()

    }

    // загрузилась погода или нет
    interface OnWeatherLoadListener {
        fun onLoaded(weatherDTO: WeatherDTO)
        fun onFailed(throwable: Throwable)
    }

}