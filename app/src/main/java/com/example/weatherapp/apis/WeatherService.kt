package com.example.weatherapp.apis

import com.example.weatherapp.models.CurrentResponse
import com.example.weatherapp.models.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("history.json")
    suspend fun getHistoricalWeather(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("dt") date: String
    ): WeatherResponse

    @GET("current.json")
    suspend fun getTodaysWeather(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("aqi") aqi: String
    ): CurrentResponse


}
