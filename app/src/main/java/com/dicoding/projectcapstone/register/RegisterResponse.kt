package com.dicoding.projectcapstone.register

data class RegisterResponse(
    val result: Result? = null,
    val success: Boolean? = null,
    val message: String? = null
)

data class Result(
    val createdAt: String? = null,
    val password: String? = null,
    val otpExpiration: String? = null,
    val id: Int? = null,
    val otpCode: String? = null,
    val isVerified: Boolean? = null,
    val email: String? = null,
    val username: String? = null,
    val updatedAt: String? = null
)

