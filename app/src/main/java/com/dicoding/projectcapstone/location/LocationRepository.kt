package com.dicoding.projectcapstone.location
import com.dicoding.projectcapstone.api.ApiService

class LocationRepository(private val apiService: ApiService)  {
    suspend fun getAllLocations() = apiService.getAllLocations()
    companion object {
        @Volatile
        private var instance: LocationRepository? = null
        fun getInstance(
            apiService: ApiService
        ): LocationRepository =
            instance ?: synchronized(this) {
                instance ?: LocationRepository(apiService)
            }.also { instance = it }
    }
}