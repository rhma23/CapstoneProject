package com.dicoding.projectcapstone.register

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val role: String
)
