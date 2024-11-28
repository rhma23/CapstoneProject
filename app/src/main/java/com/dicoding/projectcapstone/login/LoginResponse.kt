package com.dicoding.projectcapstone.login

data class LoginResponse(
	val result: Result? = null,
	val success: Boolean? = null,
	val message: String? = null
)

data class Result(
	val token: String? = null
)

