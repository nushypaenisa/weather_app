package com.example.weatherapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.database.WeatherRepository
import com.example.weatherapp.models.WeatherEntity
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: WeatherRepository
) : ViewModel() {
    fun saveWeatherData(data: WeatherEntity) {
        viewModelScope.launch {
            repository.cacheWeatherData(data)
        }
    }

    fun getWeatherData(date: String, onResult: (WeatherEntity?) -> Unit) {
        viewModelScope.launch {
            val weather = repository.getWeatherDataByDate(date)
            onResult(weather)
        }
    }
}
