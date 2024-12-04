package com.example.weatherapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.models.WeatherEntity
import com.example.weatherapp.models.WeatherHourlyData

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather_data WHERE date = :date")
    suspend fun getWeatherByDate(date: String): WeatherEntity?

    @Query("SELECT * FROM weather_hourly_data WHERE date = :date")
    suspend fun getHourlyWeatherByDate(date: String): List<WeatherHourlyData>?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHourlyWeatherData(weatherHourlyData: WeatherHourlyData)
}