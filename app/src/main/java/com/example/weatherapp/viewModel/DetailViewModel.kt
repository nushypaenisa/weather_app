package com.example.weatherapp.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.apis.WeatherService
import com.example.weatherapp.database.WeatherDao
import com.example.weatherapp.models.WeatherEntity
import com.example.weatherapp.models.WeatherHourlyData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val api: WeatherService,
    private val dao: WeatherDao
) : ViewModel() {

    // LiveData or State for storing the weather data for the selected date
    var hourlyWeatherData = mutableStateOf<List<WeatherHourlyData>?>(null)
    val errorMessage = mutableStateOf("")
    // LiveData for loading and error states (optional)
    private val _isLoading = mutableStateOf(false)

    fun getHourlyWeatherData(date: String, location: String, apiKey: String) {
        viewModelScope.launch {

            println("at  api side")
            try {
                println("at  api side4")
                // Check local cache
                val cachedHourlyWeather = dao.getHourlyWeatherByDate(date)
                println("at  api side1")
                if (cachedHourlyWeather?.isEmpty() != true) {
                    println("at  api side3")
                    hourlyWeatherData.value = cachedHourlyWeather
                    println(cachedHourlyWeather)

                } else {
                    println("at  api side5")
                    val response = api.getHistoricalWeather(apiKey, location, date)
                    println("at  api side6")
                    val hourlyData = response.forecast.forecastday.first().hour
                    println("at  api side2")
                    hourlyData?.let {

                        var itemsList: MutableList<WeatherHourlyData> = arrayListOf()
                        it.forEach { weatherHourlyData ->

                            val item =  WeatherHourlyData.fromWeatherData(weatherHourlyData, date, location)


                            itemsList.add(item)

                            // Cache response

                            dao.insertHourlyWeatherData(item)

                        }
                        hourlyWeatherData.value = itemsList.toList()

                    }


                }
            } catch (e: Exception) {
                println(e.localizedMessage)
                errorMessage.value = e.localizedMessage ?: "An error occurred"
            }
        }
    }
}