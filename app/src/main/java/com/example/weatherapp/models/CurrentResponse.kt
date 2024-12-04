package com.example.weatherapp.models


data class CurrentResponse(
    val location: Location,
    val current: Current
)

data class Current(
    val time: String,
    val temp_c: Double,
    val temp_f: Double,
    val humidity: Int,
    val wind_kph: Double,
    val condition: Condition
)

