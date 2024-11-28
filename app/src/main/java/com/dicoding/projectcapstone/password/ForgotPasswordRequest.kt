package com.dicoding.projectcapstone.password

data class ForgotPasswordRequest(
    val otp_code: String,
    val email: String,
    val newPassword: String
)

