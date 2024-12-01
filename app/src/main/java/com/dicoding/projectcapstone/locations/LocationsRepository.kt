package com.dicoding.projectcapstone.locations

import com.dicoding.projectcapstone.ApiService

class LocationsRepository(private val apiService: ApiService)  {
    suspend fun getAllLocations() = apiService.getAllLocations()
    companion object {
        @Volatile
        private var instance: LocationsRepository? = null
        fun getInstance(
            apiService: ApiService
        ): LocationsRepository =
            instance ?: synchronized(this) {
                instance ?: LocationsRepository(apiService)
            }.also { instance = it }
    }
}