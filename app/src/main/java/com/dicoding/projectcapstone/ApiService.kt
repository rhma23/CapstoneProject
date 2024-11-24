package com.dicoding.projectcapstone

import com.dicoding.projectcapstone.register.RegisterRequest
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import com.dicoding.projectcapstone.register.RegisterResponse
import retrofit2.http.Body

interface ApiService {
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse
}