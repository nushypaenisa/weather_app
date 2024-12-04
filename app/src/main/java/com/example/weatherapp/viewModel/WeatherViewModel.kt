package com.example.weatherapp.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.apis.WeatherService
import com.example.weatherapp.database.WeatherDao
import com.example.weatherapp.models.CurrentResponse
import com.example.weatherapp.models.WeatherEntity
import com.example.weatherapp.models.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val api: WeatherService,
    private val dao: WeatherDao
) : ViewModel() {

    val weatherData = mutableStateOf<WeatherResponse?>(null)
    val todaysData = mutableStateOf<CurrentResponse?>(null)

    val entityData = mutableStateOf<WeatherEntity?>(null)

    val errorMessage = mutableStateOf("")

    fun fetchWeather(date: String, location: String, apiKey: String) {
        viewModelScope.launch {

            try {
                // Check local cache
                val cachedWeather = dao.getWeatherByDate(date)
                if (cachedWeather != null) {

                    entityData.value = cachedWeather
                } else {
                    val response = api.getHistoricalWeather(apiKey, location, date)

                    weatherData.value = response

                    entityData.value =  WeatherEntity.fromWeatherData(response.forecast.forecastday[0].day, date, location)

                       // Cache response

                    dao.insertWeather(entityData.value!!)

                }
            } catch (e: Exception) {
                errorMessage.value = e.localizedMessage ?: "An error occurred"
            }
        }
    }
    fun fetchTodaysWeather(date: String, location: String, apiKey: String) {
        viewModelScope.launch {

            try {
                // Check local cache
                val cachedWeather = dao.getWeatherByDate(date)
                if (cachedWeather != null) {

                    entityData.value = cachedWeather
                } else {
                    val response = api.getTodaysWeather(apiKey, location, date)

                    todaysData.value = response

                    entityData.value =  WeatherEntity.fromWeatherCurrentData(response.current, date, location)

                    // Cache response

                    dao.insertWeather(entityData.value!!)

                }
            } catch (e: Exception) {
                errorMessage.value = e.localizedMessage ?: "An error occurred"
            }
        }
    }

}
