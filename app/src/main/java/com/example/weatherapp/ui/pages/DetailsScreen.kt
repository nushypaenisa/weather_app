package com.example.weatherapp.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherapp.models.WeatherHourlyData
import com.example.weatherapp.utils.WeatherUtils
import com.example.weatherapp.viewModel.DetailsViewModel
import java.util.TimeZone

@Composable
fun DetailsScreen(navController: NavController, viewModel: DetailsViewModel = hiltViewModel(), date: String) {
    val weather = viewModel.hourlyWeatherData.value

    LaunchedEffect(Unit) {

        viewModel.getHourlyWeatherData(date,"Nairobi", "890ceead01424b0398894102240212")

    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF87CEEB) // Light blue background
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            Button(onClick = { navController.popBackStack() }) {
                Text("Go Back",
                    color = Color.White )
            }

            Text("Weather Details for $date",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black)



            weather?.let {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
//                        top = 16.dp,
//                        bottom = 16.dp,
                    ),
                ) {


                    items(it) { hour ->
                        HourlyWeatherItem(hour)
                    }
                }
            } ?: run {
                Text("No data available.")
            }
            // Back button

        }
    }

}

@Composable
fun WeatherDetailCard(hour: WeatherHourlyData) {//was hour


        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Time: ${hour.time}")
                Text("Temp: ${hour.temperature_c}°C / ${hour.temperature_f}°F")
                Text("Condition: ${hour.weatherCondition}")
                Text("Humidity: ${hour.humidity}%")
                Text("Wind Speed: ${hour.windSpeed} kph")
            }
        }

}


@Composable
fun HourlyWeatherItem(hour: WeatherHourlyData) {

    val parts = hour.time.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

    val tz: TimeZone = TimeZone.getDefault()

    val formattedTime = WeatherUtils.formatTime(parts[1],  tz.getDisplayName(
        false,
        TimeZone.SHORT
    ))

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
                ),
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {





            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    text = hour.weatherCondition,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = formattedTime,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )

                Text("Temp: ${hour.temperature_c}°C / ${hour.temperature_f}°F")
                Text("Humidity: ${hour.humidity}%")
                Text("Wind Speed: ${hour.windSpeed} kph")
            }
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