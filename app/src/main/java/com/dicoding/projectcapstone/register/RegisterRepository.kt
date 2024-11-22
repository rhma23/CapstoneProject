package com.dicoding.projectcapstone.register

import com.dicoding.projectcapstone.ApiService


class RegisterRepository(
    private val apiService: ApiService
) {
    suspend fun register(username: String, email: String, password: String, role: String): RegisterResponse {
        val request = RegisterRequest(username, email, password, role)
        return apiService.register(request)
    }


    companion object {
        @Volatile
        private var instance: RegisterRepository? = null
        fun getInstance(
            apiService: ApiService
        ): RegisterRepository =
            instance ?: synchronized(this) {
                instance ?: RegisterRepository(apiService)
            }.also { instance = it }
    }
}