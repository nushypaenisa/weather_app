package com.example.weatherapp.ui.pages

import android.widget.CalendarView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherapp.models.WeatherEntity
import com.example.weatherapp.viewModel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable

fun HomeScreen(navController: NavController,  viewModel: WeatherViewModel = hiltViewModel()) {

    val weatherEntity = viewModel.entityData.value
    val todayWeather = remember { mutableStateOf<WeatherEntity?>(null) }
    val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    LaunchedEffect(Unit) {
        viewModel.fetchTodaysWeather("no","Nairobi", "890ceead01424b0398894102240212")
        viewModel.fetchWeather("2024-11-26","Nairobi", "890ceead01424b0398894102240212")

    }
    showDatePicker(navController)
    Column(modifier = Modifier.fillMaxSize()) {

       // CalendarView(onDateSelected = { date -> viewModel.fetchWeather("2024-11-26","Nairobi", "890ceead01424b0398894102240212") })
        showDatePicker(navController)
        if (weatherEntity != null) {
            WeatherCard(hourData = weatherEntity)
        }
    }
}



@Composable
fun WeatherCard(hourData: WeatherEntity) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Today's Weather")
            Text("Temperature: ${hourData.temperature_c}°C / ${hourData.temperature_f}°F")
            Text("Humidity: ${hourData.humidity}%")
            Text("Wind Speed: ${hourData.windSpeed} kph")
            Text("Condition: ${hourData.weatherCondition}")
        }
    }
}
@Composable
fun showDatePicker(navController: NavController) {
    AndroidView(
        { CalendarView(it) },
        modifier = Modifier.wrapContentWidth(),
        update = { views ->
            views.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
                navController.navigate("details/$year-$month-$dayOfMonth")
            }
        }
    )
}