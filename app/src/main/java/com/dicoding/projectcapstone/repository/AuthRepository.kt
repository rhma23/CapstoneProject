package com.dicoding.projectcapstone.repository

import com.dicoding.projectcapstone.API.ApiService
import com.dicoding.projectcapstone.login.LoginRequest
import com.dicoding.projectcapstone.login.LoginResponse
import com.dicoding.projectcapstone.otp.OtpRequest
import com.dicoding.projectcapstone.otp.OtpResponse
import com.dicoding.projectcapstone.otp.ResendOtpRequest
import com.dicoding.projectcapstone.otp.ResendOtpResponse
import com.dicoding.projectcapstone.password.ForgotPasswordRequest
import com.dicoding.projectcapstone.password.ForgotPasswordResponse
import com.dicoding.projectcapstone.register.RegisterRequest
import com.dicoding.projectcapstone.register.RegisterResponse
import com.dicoding.projectcapstone.user.UserDataResponse


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

    suspend fun resendOtp(email: String): ResendOtpResponse {
        val request = ResendOtpRequest(email)
        return apiService.resendOtp(request)
    }

    suspend fun resendOtpForgotPassword(email: String): ResendOtpResponse {
        val request = ResendOtpRequest(email)
        return apiService.resendOtp(request)
    }

    suspend fun resetPassword(otp_code: String, email: String, newPassword: String): ForgotPasswordResponse {
        val request = ForgotPasswordRequest(otp_code, email, newPassword)
        return apiService.resetPassword(request)
    }

    suspend fun getUserData(): UserDataResponse {
        return apiService.getUserData()
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