package com.example.weatherapp.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.models.WeatherEntity
import com.example.weatherapp.models.HourData
import com.example.weatherapp.viewModel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable

//fun HomeScreen(viewModel: WeatherViewModel = viewModel()) {
//    //val weatherData by homeViewModel.weatherData
//    val weatherDat = viewModel.weatherData.value
//
////    val isLoading by homeViewModel.isLoading
////    val errorMessage by homeViewModel.errorMessage
//
//    // Trigger data loading when screen is first displayed
//    LaunchedEffect(Unit) {
//       viewModel.fetchWeather("2024/11/26","Nairobi", "890ceead01424b0398894102240212")
//
////    }/ Example: Fetch data for Nairobi
//    }
//
////    if (isLoading) {
////        // Show loading indicator
////        CircularProgressIndicator()
////    }
////
////    if (errorMessage != null) {
////        // Show error message
////        Text(text = errorMessage ?: "Unknown error", color = Color.Red)
////    }
//
//    weatherDat?.let {
//        // Display weather data
//        Text(text = "Temperature: ${it}°C")
////        Text(text = "Humidity: ${it.humidity}%")
////        Text(text = "Condition: ${it.weatherCondition}")
//    }
//}
fun HomeScreen(viewModel: WeatherViewModel = viewModel()) {

    val weatherEntity = viewModel.entityData.value
    val todayWeather = remember { mutableStateOf<WeatherEntity?>(null) }
    val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    LaunchedEffect(Unit) {
        viewModel.fetchTodaysWeather("no","Nairobi", "890ceead01424b0398894102240212")
        viewModel.fetchWeather("2024-11-26","Nairobi", "890ceead01424b0398894102240212")

    }
    Column(modifier = Modifier.fillMaxSize()) {
        CalendarView(onDateSelected = { date -> viewModel.fetchWeather("2024-11-26","Nairobi", "890ceead01424b0398894102240212") })

        if (weatherEntity != null) {
            WeatherCard(hourData = weatherEntity)
        }
    }
}

@Composable
fun CalendarView(onDateSelected: (String) -> Unit) {
    // Dummy implementation of a calendar
    Button(
        onClick = { onDateSelected("2024-12-01") },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Pick a Date")
    }
}

@Composable
fun WeatherCard(hourData: WeatherEntity) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Todays Weathe")
            Text("Temperature: ${hourData.temperature_c}°C / ${hourData.temperature_f}°F")
            Text("Humidity: ${hourData.humidity}%")
            Text("Wind Speed: ${hourData.windSpeed} kph")
            Text("Condition: ${hourData.weatherCondition}")
        }
    }
}
