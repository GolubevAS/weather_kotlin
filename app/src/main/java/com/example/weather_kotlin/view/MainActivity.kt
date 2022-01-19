package com.example.weather_kotlin.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import com.example.weather_kotlin.R
import com.example.weather_kotlin.databinding.ActivityMainBinding
import com.example.weather_kotlin.model.MainWorker
import com.example.weather_kotlin.model.RepositoryImpl
import com.google.gson.Gson


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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.contacts) {

            startActivity(Intent (this, ContactsActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

