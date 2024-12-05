package com.example.weatherapp.ui.pages

import android.widget.CalendarView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
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
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CheckCircle
//import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen(navController: NavController,  viewModel: WeatherViewModel = hiltViewModel()) {

    val weatherEntity = viewModel.entityData.value
    val todayWeather = remember { mutableStateOf<WeatherEntity?>(null) }
    val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    LaunchedEffect(Unit) {

        viewModel.fetchTodaysWeather("no","Nairobi", "890ceead01424b0398894102240212")

    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF87CEEB) // Light blue background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Section: City Name
            Text(
                text = "Nairobi",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Magenta,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))
            // Display Hourly Weather Data
            Text(
                text = "Weather for ${selectedDate.format(DateTimeFormatter.ofPattern("MMM d, yyyy"))}",
                style = MaterialTheme.typography.titleSmall,
                color = Color.White
            )



            // Display Weather Data Below
            if (weatherEntity != null) {

                Text(
                    text = "${weatherEntity.temperature_c}°C / ${weatherEntity.temperature_f}°F ${weatherEntity.weatherCondition}",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Wind: ${weatherEntity.windSpeed} kph | Humidity:  ${weatherEntity.humidity}%",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }


            Spacer(modifier = Modifier.height(16.dp))

            // Display Historical Weather Data
            Text(
                text = "Historical Weather",
                style = MaterialTheme.typography.titleSmall,
                color = Color.Magenta
            )
            // Calendar
            Column(modifier = Modifier.fillMaxSize()) {
                showDatePicker(navController)
            }


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