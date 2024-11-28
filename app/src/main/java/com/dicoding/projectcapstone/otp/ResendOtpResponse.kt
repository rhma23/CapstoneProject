package com.dicoding.projectcapstone.otp

data class ResendOtpResponse(
	val result: String? = null,
	val success: Boolean? = null,
	val otpExpiration: String? = null,
	val message: String? = null,
	val email: String? = null
)

