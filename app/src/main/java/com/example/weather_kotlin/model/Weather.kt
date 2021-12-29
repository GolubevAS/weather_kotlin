package com.example.weather_kotlin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Weather(
    val city : City = getDefaultCity(),
    val temperature : Int = 0,
    val feelsLike : Int = 0,
    val description : String = ""
): Parcelable

@Parcelize
data class City (
    val name : String = "Москва",
    val nameEn : String = "Moscow",
    val lat : Double = 0.0,
    val lon : Double = 0.0
) : Parcelable

fun getDefaultCity() = City ("Москва", "Moscow",55.755826, 37.617299900000035)

fun getWorldCity () : List<Weather> = listOf(
        Weather(City("Лондон", "Moscow",51.5085300, -0.1257400), 1, 2),
        Weather(City("Токио", "Tokyo", 35.6895000, 139.6917100), 3, 4),
        Weather(City("Париж", "Paris",48.8534100, 2.3488000), 5, 6),
        Weather(City("Берлин", "Berlin",52.52000659999999, 13.404953999999975), 7, 8),
        Weather(City("Рим", "Rome",41.9027835, 12.496365500000024), 9, 10),
        Weather(City("Минск", "Minsk",53.90453979999999, 27.561524400000053), 11, 12),
        Weather(City("Стамбул", "Istanbul",41.0082376, 28.97835889999999), 13, 14),
        Weather(City("Вашингтон", "Washington",38.9071923, -77.03687070000001), 15, 16),
        Weather(City("Киев", "Kiev",50.4501, 30.523400000000038), 17, 18),
        Weather(City("Пекин", "Beijing",39.90419989999999, 116.40739630000007), 19, 20)
    )


fun getRussianCities(): List<Weather> =  listOf(
        Weather(City("Москва", "Moscow",55.755826, 37.617299900000035), 1, 2),
        Weather(City("Санкт-Петербург", "Saint_Petersburg",59.9342802, 30.335098600000038), 3, 3),
        Weather(City("Новосибирск", "Novosibirsk",55.00835259999999, 82.93573270000002), 5, 6),
        Weather(City("Екатеринбург", "Yekaterinburg",56.83892609999999, 60.60570250000001), 7, 8),
        Weather(City("Нижний Новгород", "Nizhny Novgorod",56.2965039, 43.936059), 9, 10),
        Weather(City("Казань", "Kazan",55.8304307, 49.06608060000008), 11, 12),
        Weather(City("Челябинск", "Chelyabinsk",55.1644419, 61.4368432), 13, 14),
        Weather(City("Омск", "Omsk",54.9884804, 73.32423610000001), 15, 16),
        Weather(City("Ростов-на-Дону", "Rostov-on-Don",47.2357137, 39.701505), 17, 18),
        Weather(City("Уфа", "Ufa",54.7387621, 55.972055400000045), 19, 20)
    )


