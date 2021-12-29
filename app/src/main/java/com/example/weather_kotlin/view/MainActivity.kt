package com.example.weather_kotlin.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.weather_kotlin.R
import com.example.weather_kotlin.databinding.ActivityMainBinding
import com.example.weather_kotlin.model.MainWorker
import com.example.weather_kotlin.model.RepositoryImpl
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.stream.Collector
import java.util.stream.Collectors

class MainActivity : AppCompatActivity() {

    private  val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val json = Gson().toJson(RepositoryImpl.getWeatherFromServer())

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, MainFragment.newInstance())
            .commit()

        MainWorker.startWorker(this)
    }
}

