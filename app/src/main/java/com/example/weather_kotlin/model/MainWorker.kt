package com.example.weather_kotlin.model

import android.content.Context
import android.content.ContextParams
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.*

class MainWorker (context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {


    @RequiresApi(Build.VERSION_CODES.N)
    override fun doWork(): Result {

        var result = Result.success()

        WeatherLoader.load(city = City(), object : WeatherLoader.OnWeatherLoadListener {
            override fun onLoaded(weatherDTO: WeatherDTO) {
                result = Result.success()
            }

            override fun onFailed(throwable: Throwable) {
                result = Result.failure()
            }

        })

       return result
    }

companion object {
    fun startWorker(context: Context) {
        val uploadWorkRequest : WorkRequest =
            OneTimeWorkRequest.Builder(MainWorker ::class.java)
                .setConstraints(Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                .build())
                .build()

        WorkManager.getInstance(context).enqueue(uploadWorkRequest)

    }





}

}