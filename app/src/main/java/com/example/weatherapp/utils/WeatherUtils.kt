package com.example.weatherapp.utils

import java.text.SimpleDateFormat
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.util.*

object WeatherUtils {

    fun parseDateTime(startTs: Long, endTs: Long, localizedDateTime: String): Pair<String, String> {
        val dateUtcStart = LocalDateTime.ofInstant(Instant.ofEpochMilli(startTs), ZoneOffset.UTC)
        val dateUtcEnd = LocalDateTime.ofInstant(Instant.ofEpochMilli(endTs), ZoneOffset.UTC)

        val formatter = DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .parseDefaulting(ChronoField.YEAR_OF_ERA, dateUtcStart.year.toLong())
            .append(DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm"))
            .toFormatter(Locale.ENGLISH)

        val localDateTime = LocalDateTime.parse(localizedDateTime, formatter)

        val duration = Duration.between(dateUtcStart, localDateTime)
        val offset = ZoneOffset.ofTotalSeconds(duration.seconds.toInt())
        val startWithOffset = dateUtcStart.atOffset(offset)
        val endWithOffset = dateUtcEnd.atOffset(offset)

        return Pair(startWithOffset.toString(), endWithOffset.toString())
    }

    // Convert temperature from Celsius to Fahrenheit
    fun celsiusToFahrenheit(celsius: Double): Double {
        return (celsius * 9 / 5) + 32
    }

    // Convert temperature from Fahrenheit to Celsius
    fun fahrenheitToCelsius(fahrenheit: Double): Double {
        return (fahrenheit - 32) * 5 / 9
    }

    // Format the date to a readable string (e.g., "12:00 PM")
    fun formatTime(time: String, currentTimezone: String): String {
        val originalFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        originalFormat.timeZone = TimeZone.getTimeZone(currentTimezone)
        val date = originalFormat.parse(time) ?: return time
        val outputFormat = SimpleDateFormat("h:mm a", Locale.getDefault()) // "12:00 PM"
        return outputFormat.format(date)
    }

    // Format the date to a readable date (e.g., "12th Jan, 2024")
    fun formatDate(date: String, currentTimezone: String): String {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        originalFormat.timeZone = TimeZone.getTimeZone(currentTimezone)
        val parsedDate = originalFormat.parse(date) ?: return date
        val outputFormat = SimpleDateFormat("d MMM, yyyy", Locale.getDefault()) // "12th Jan, 2024"
        return outputFormat.format(parsedDate)
    }

    // Format temperature with unit (e.g., "27°C" or "80°F")
    fun formatTemperature(temperature: Double, isCelsius: Boolean): String {
        return if (isCelsius) {
            "${temperature.toInt()}°C"
        } else {
            "${celsiusToFahrenheit(temperature).toInt()}°F"
        }
    }

    // Format wind speed (e.g., "15 km/h")
    fun formatWindSpeed(windSpeed: Double): String {
        return "${windSpeed.toInt()} km/h"
    }

    // Format humidity (e.g., "65%")
    fun formatHumidity(humidity: Double): String {
        return "${humidity.toInt()}%"
    }

    // Get a user-friendly weather condition (e.g., "Cloudy", "Sunny")
    fun getWeatherConditionDescription(condition: String): String {
        return when (condition) {
            "Clear" -> "Sunny"
            "Clouds" -> "Cloudy"
            "Rain" -> "Rainy"
            "Snow" -> "Snowy"
            "Thunderstorm" -> "Thunderstorms"
            else -> "Unknown"
        }
    }
}