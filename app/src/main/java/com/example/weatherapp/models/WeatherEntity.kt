package com.example.weatherapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_data")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val date: String,
    val location: String,
    val temperature_f: Double,
    val temperature_c: Double,
    val humidity: Int,
    val windSpeed: Double,
    val weatherCondition: String
){
    // Convert WeatherData to WeatherEntity for database insertion
    companion object {
        fun fromWeatherData(data: Day, date: String, location: String): WeatherEntity {
            return WeatherEntity(
                date = date,
                location = location,
                temperature_f = data.maxtemp_f,
                temperature_c = data.maxtemp_c,
                humidity = data.avghumidity,
                windSpeed = data.maxwind_kph,
                weatherCondition = data.condition.text,
            )
        }

        fun fromWeatherHourData(data: HourData, date: String, location: String): WeatherEntity {
            return WeatherEntity(
                date = date,
                location = location,
                temperature_f = data.temp_f,
                temperature_c = data.temp_c,
                humidity = data.humidity,
                windSpeed = data.wind_kph,
                weatherCondition = data.condition.text,
            )
        }
        fun fromWeatherCurrentData(data: Current, date: String, location: String): WeatherEntity {
            return WeatherEntity(
                date = date,
                location = location,
                temperature_f = data.temp_f,
                temperature_c = data.temp_c,
                humidity = data.humidity,
                windSpeed = data.wind_kph,
                weatherCondition = data.condition.text,
            )
        }

    }
}