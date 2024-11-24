package com.dicoding.projectcapstone.otp

data class OtpRequest(
    val email: String,
    val otp_code: String,
)
