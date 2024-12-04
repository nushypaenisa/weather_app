package com.example.weatherapp.models

data class WeatherResponse(
    val location: Location,
    val forecast: Forecast
)

data class Location(val name: String, val region: String, val country: String)

data class Forecast(val forecastday: List<ForecastDay>)

data class ForecastDay(val date: String, val hour: List<HourData>, val day: Day)

data class HourData(
    val time: String,
    val temp_c: Double,
    val temp_f: Double,
    val humidity: Int,
    val wind_kph: Double,
    val condition: Condition
)

data class Day(
    val time: String,
    val maxtemp_c: Double,
    val maxtemp_f: Double,
    val avghumidity: Int,
    val maxwind_kph: Double,
    val condition: Condition

)

data class Condition(val text: String, val icon: String)
