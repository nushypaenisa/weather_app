package com.example.weatherapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.models.WeatherEntity
import com.example.weatherapp.models.WeatherHourlyData

@Database(entities = [WeatherEntity::class, WeatherHourlyData::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}