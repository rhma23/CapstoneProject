package com.dicoding.projectcapstone.repository

import com.dicoding.projectcapstone.ApiService
import com.dicoding.projectcapstone.login.LoginRequest
import com.dicoding.projectcapstone.login.LoginResponse
import com.dicoding.projectcapstone.otp.OtpRequest
import com.dicoding.projectcapstone.otp.OtpResponse
import com.dicoding.projectcapstone.otp.ResendOtpRequest
import com.dicoding.projectcapstone.register.RegisterRequest
import com.dicoding.projectcapstone.register.RegisterResponse


class AuthRepository(
    private val apiService: ApiService
) {
    suspend fun register(username: String, email: String, password: String, role: String): RegisterResponse {
        val request = RegisterRequest(username, email, password, role)
        return apiService.register(request)
    }

    suspend fun login(email: String, password: String): LoginResponse {
        val request = LoginRequest(email, password)
        return apiService.login(request)
    }

    suspend fun verify(email: String, otp_code: String): OtpResponse {
        val request = OtpRequest(email, otp_code)
        return apiService.verify(request)
    }

    suspend fun resendOtp(email: String): OtpResponse {
        val request = ResendOtpRequest(email)
        return apiService.resendOtp(request)
    }
    companion object {
        @Volatile
        private var instance: AuthRepository? = null
        fun getInstance(
            apiService: ApiService
        ): AuthRepository =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(apiService)
            }.also { instance = it }
    }
}