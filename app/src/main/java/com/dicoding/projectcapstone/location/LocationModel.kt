package com.dicoding.projectcapstone.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LocationModel(private val repository: LocationRepository) : ViewModel() {
    private val _locations = MutableLiveData<List<LocationResponse>>()
    val locations: LiveData<List<LocationResponse>> get() = _locations
    fun getAllLocations() {
        viewModelScope.launch {
            try {
                val result = repository.getAllLocations()
                _locations.postValue(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}