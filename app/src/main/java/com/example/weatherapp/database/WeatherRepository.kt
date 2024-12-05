package com.example.weatherapp.database

import android.content.Context
import com.example.weatherapp.models.WeatherEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(context: Context, passphrase: String) {
    private val database = WeatherDatabase.getDatabase(context, passphrase)
    private val weatherDao = database.weatherDao()

    suspend fun cacheWeatherData(data: WeatherEntity) = withContext(Dispatchers.IO) {
        weatherDao.insertWeather(data)
    }

    suspend fun getWeatherDataByDate(date: String): WeatherEntity? = withContext(Dispatchers.IO) {
        weatherDao.getWeatherByDate(date)
    }
}
