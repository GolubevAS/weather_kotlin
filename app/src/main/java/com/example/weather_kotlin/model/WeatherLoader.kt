package com.example.weather_kotlin.model


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors


object WeatherLoader {


    private val client: OkHttpClient = OkHttpClient.Builder()
        .callTimeout(1000, TimeUnit.MILLISECONDS)
        .connectTimeout(1000, TimeUnit.MILLISECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })
        .addInterceptor(Interceptor { chain ->
            chain.proceed(chain.request()
                .newBuilder()
                .addHeader("YOUR_API_KEY", "2464e2e4321ac072c7a8011bf47da68e")
                .build())
        })
        .build()


    private val weatherAPI : WeatherAPI = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()
        .create(WeatherAPI::class.java)


    private const val YOUR_API_KEY = "2464e2e4321ac072c7a8011bf47da68e"

    @RequiresApi(Build.VERSION_CODES.N)
    fun load(city: City, listener: OnWeatherLoadListener) {


        var urlConnection: HttpURLConnection? = null

        try {
            val uri =
                URL("https://api.openweathermap.org/data/2.5/weather?q=${city.nameEn}&appid=${YOUR_API_KEY}&units=metric&lang=ru")

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

            listener.onLoaded(weatherDTO)

        } catch (e: Exception) {
            listener.onFailed(e)
            Log.e("DEBUGLOG", "FAIL CONNECTION")
        } finally {
            urlConnection?.disconnect()
        }

    }


    fun loadOkHttp(city: City, listener: OnWeatherLoadListener) {

        val request: Request = Request.Builder()
            .get()
            .addHeader("YOUR_API_KEY", YOUR_API_KEY)
            .url("https://api.openweathermap.org/data/2.5/weather?q=${city.nameEn}&appid=${YOUR_API_KEY}&units=metric&lang=ru")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                listener.onFailed(e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val weatherDTO =
                        Gson().fromJson(response.body?.string(), WeatherDTO::class.java)
                    listener.onLoaded(weatherDTO)
                } else {
                    listener.onFailed(Exception(response.body?.string()))
                }
            }
        })
    }

    fun loadRetrofit(city: City, listener: OnWeatherLoadListener) {

        weatherAPI.getWeather(city.nameEn)
            .enqueue(object : retrofit2.Callback<WeatherDTO> {
                override fun onResponse(
                    call: retrofit2.Call<WeatherDTO>,
                    response: retrofit2.Response<WeatherDTO>
                ) {
                    if (response.isSuccessful) {
                        val weatherDTO =
                           response.body()?.let {
                               listener.onLoaded(it)
                           }
                    } else {
                        listener.onFailed(Exception(response.message()))
                    }
                }

                override fun onFailure(call: retrofit2.Call<WeatherDTO>, t: Throwable) {
                    listener.onFailed(t)
                }

            })
    }


    // загрузилась погода или нет
    interface OnWeatherLoadListener {
        fun onLoaded(weatherDTO: WeatherDTO)
        fun onFailed(throwable: Throwable)
    }

}