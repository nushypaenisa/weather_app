package com.example.weatherapp.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.weatherapp.models.WeatherHourlyData
import com.example.weatherapp.viewModel.DetailsViewModel
import com.example.weatherapp.viewModel.WeatherViewModel


@Composable
fun DetailsScreen(viewModel: DetailsViewModel, date: String) {
    val weather = viewModel.hourlyWeatherData.value

    LaunchedEffect(Unit) {
        viewModel.getHourlyWeatherData("2024/11/26","Nairobi", "890ceead01424b0398894102240212")

    }
    weather?.let {
        LazyColumn {
            items(it) { hour ->
                WeatherDetailCard(hour)
            }
        }
    } ?: run {
        Text("No data available.")
    }
}

@Composable
fun WeatherDetailCard(hour: WeatherHourlyData) {//was hour
    Card {
        Column {
            Text("Time: ${hour.time}")
            Text("Temp: ${hour.temperature_c}°C / ${hour.temperature_f}°F")
            Text("Condition: ${hour.weatherCondition}")
            Text("Humidity: ${hour.humidity}%")
            Text("Wind Speed: ${hour.windSpeed} kph")
        }
    }
}