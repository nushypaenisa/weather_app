package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.database.WeatherRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()

    }
}