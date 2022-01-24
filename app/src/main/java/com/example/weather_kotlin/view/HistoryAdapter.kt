package com.example.weather_kotlin.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_kotlin.R
import com.example.weather_kotlin.model.Weather

class HistoryAdapter(private var items: List<Weather>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemViewHolder {

        return HistoryItemViewHolder(
           LayoutInflater.from(parent.context).inflate(R.layout.activity_history, parent, false)
       )
    }

    override fun onBindViewHolder(holder: HistoryItemViewHolder, position: Int) {

        val weather = items[position]

        holder.itemView.apply {
            findViewById<TextView>(R.id.city_label).text = weather.city.name
            findViewById<TextView>(R.id.temperature_label).text = weather.temperature.toString()
        }

    }

    override fun getItemCount(): Int = items.size


    class HistoryItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
}