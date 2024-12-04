package com.example.weatherapp.viewModel

import androidx.compose.runtime.mutableStateOf
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
    val hourlyWeatherData = mutableStateOf<List<WeatherHourlyData>?>(null)
    val errorMessage = mutableStateOf("")
    // LiveData for loading and error states (optional)
    private val _isLoading = mutableStateOf(false)

    fun getHourlyWeatherData(date: String, location: String, apiKey: String) {
        viewModelScope.launch {

            try {
                // Check local cache
                val cachedHourlyWeather = dao.getHourlyWeatherByDate(date)
                if (cachedHourlyWeather != null) {

                    hourlyWeatherData.value = cachedHourlyWeather
                } else {
                    val response = api.getHistoricalWeather(apiKey, location, date)

                    val hourlyData = response.forecast.forecastday.first().hour

                    hourlyData?.let {
                        // Save to local database for caching
                        it.forEach { weatherHourlyData ->

                            val items =  WeatherEntity.fromWeatherHourData(weatherHourlyData, date, location)

                            // Cache response

                            dao.insertWeather(items)
                        }
                    }


                }
            } catch (e: Exception) {
                errorMessage.value = e.localizedMessage ?: "An error occurred"
            }
        }
    }
}