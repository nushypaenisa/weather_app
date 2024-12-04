package com.example.weatherapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_hourly_data")
data class WeatherHourlyData(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val date: String,
    val location: String,
    val time: String,         // Time of the hour (e.g., 10:00, 11:00, etc.)
    val temperature_f: Double, // Temperature in Celsius
    val temperature_c: Double, // Temperature in Celsius
    val humidity: Double,    // Humidity percentage
    val windSpeed: Double,   // Wind speed in km/h
    val weatherCondition: String // Weather condition (e.g., "Sunny", "Cloudy")
){
    // Convert WeatherData to WeatherEntity for database insertion
    companion object {
        fun fromWeatherData(data: HourData, date: String, location: String): WeatherEntity {
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