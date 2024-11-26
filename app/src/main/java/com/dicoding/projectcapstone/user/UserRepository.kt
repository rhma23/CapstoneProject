package com.dicoding.projectcapstone.user

import com.dicoding.projectcapstone.ApiService

class UserRepository(private val apiService: ApiService)  {
    suspend fun getUserData() = apiService.getData()
    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService)
            }.also { instance = it }
    }
}