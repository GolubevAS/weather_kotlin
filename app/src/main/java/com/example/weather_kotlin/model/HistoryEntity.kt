package com.example.weather_kotlin.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "HistoryEntity")
data class HistoryEntity(

    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val city : String,
    val temperature: Float,
   // val condition: String,
    val timestamp: Long

)
