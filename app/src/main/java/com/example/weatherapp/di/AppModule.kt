package com.example.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.example.weatherapp.apis.RetrofitInstance
import com.example.weatherapp.apis.WeatherService
import com.example.weatherapp.database.WeatherDao
import com.example.weatherapp.database.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    fun provideWeatherApi(): WeatherService = RetrofitInstance.api

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): WeatherDatabase =
        Room.databaseBuilder(context, WeatherDatabase::class.java, "weather_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideWeatherDao(db: WeatherDatabase): WeatherDao = db.weatherDao()
}