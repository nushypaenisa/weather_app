package com.example.weatherapp.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherapp.models.WeatherHourlyData
import com.example.weatherapp.viewModel.DetailsViewModel
import com.example.weatherapp.viewModel.WeatherViewModel


@Composable
fun DetailsScreen(navController: NavController, viewModel: DetailsViewModel = hiltViewModel(), date: String) {
    val weather = viewModel.hourlyWeatherData.value


    LaunchedEffect(Unit) {
        viewModel.getHourlyWeatherData("2024/11/26","Nairobi", "890ceead01424b0398894102240212")

    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Details for $date", style = MaterialTheme.typography.titleLarge)

        // Fetch weather for the selected date


//        // Display weather details
//        weather?.let { data ->
//            Text("Temperature: ${data.temperatureC}°C")
//            Text("Condition: ${data.condition}")
//        } ?: Text("Loading weather details...")
        weather?.let {
            LazyColumn {

                items(it) { hour ->
                    WeatherDetailCard(hour)
                }
            }
        } ?: run {
            Text("No data available.")
        }
        // Back button
        Button(onClick = { navController.popBackStack() }) {
            Text("Go Back")
        }
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


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DateInputSample() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        val state = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)
        DatePicker(state = state, modifier = Modifier.padding(16.dp))

        Text(
            "Entered date timestamp: ${state.selectedDateMillis ?: "no input"}",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}