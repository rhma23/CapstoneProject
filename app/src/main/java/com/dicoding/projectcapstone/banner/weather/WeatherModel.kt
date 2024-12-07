package com.dicoding.projectcapstone.banner.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.projectcapstone.banner.BannerRepository
import com.dicoding.projectcapstone.banner.Weather
import kotlinx.coroutines.launch

class WeatherModel(private val repository: WeatherRepository) : ViewModel() {

    val weather: LiveData<WeatherResponse> = repository.weather
    val errorMessage: LiveData<String> = repository.errorMessage

    fun fetchDataWeather() {
        viewModelScope.launch {
            repository.getDataWeather()
        }
    }
}