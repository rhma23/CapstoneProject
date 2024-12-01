package com.dicoding.projectcapstone.API

import com.dicoding.projectcapstone.login.LoginRequest
import com.dicoding.projectcapstone.login.LoginResponse
import com.dicoding.projectcapstone.otp.OtpRequest
import com.dicoding.projectcapstone.otp.OtpResponse
import com.dicoding.projectcapstone.otp.ResendOtpRequest
import com.dicoding.projectcapstone.otp.ResendOtpResponse
import com.dicoding.projectcapstone.password.ForgotPasswordRequest
import com.dicoding.projectcapstone.password.ForgotPasswordResponse
import com.dicoding.projectcapstone.product.GetAllProductResponse
import com.dicoding.projectcapstone.register.RegisterRequest
import retrofit2.http.POST
import com.dicoding.projectcapstone.register.RegisterResponse
import com.dicoding.projectcapstone.user.UserDataResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
//    Auth
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @POST("auth/verify")
    suspend fun verify(@Body request: OtpRequest): OtpResponse

    @POST("auth/resend_otp")
    suspend fun resendOtp(@Body request: ResendOtpRequest): ResendOtpResponse

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("auth/ResetPassword")
    suspend fun resetPassword(@Body request: ForgotPasswordRequest): ForgotPasswordResponse

    @GET("auth/userData")
    suspend fun getUserData(
        @Header("Authorization") authHeader: String
    ): UserDataResponse

//    Product
    @GET("product")
    suspend fun getAllProducts(): List<GetAllProductResponse>

}