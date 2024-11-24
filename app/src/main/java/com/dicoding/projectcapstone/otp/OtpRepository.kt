package com.dicoding.projectcapstone.otp

import com.dicoding.projectcapstone.ApiService


class OtpRepository(
    private val apiService: ApiService
) {
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
        private var instance: OtpRepository? = null
        fun getInstance(
            apiService: ApiService
        ): OtpRepository =
            instance ?: synchronized(this) {
                instance ?: OtpRepository(apiService)
            }.also { instance = it }
    }
}