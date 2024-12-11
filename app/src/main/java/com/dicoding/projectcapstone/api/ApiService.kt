package com.dicoding.projectcapstone.api

import com.dicoding.projectcapstone.banner.weather.WeatherResponse
import com.dicoding.projectcapstone.location.LocationResponse
import com.dicoding.projectcapstone.login.LoginRequest
import com.dicoding.projectcapstone.login.LoginResponse
import com.dicoding.projectcapstone.otp.OtpRequest
import com.dicoding.projectcapstone.otp.OtpResponse
import com.dicoding.projectcapstone.otp.ResendOtpRequest
import com.dicoding.projectcapstone.otp.ResendOtpResponse
import com.dicoding.projectcapstone.password.ForgotPasswordRequest
import com.dicoding.projectcapstone.password.ForgotPasswordResponse
import com.dicoding.projectcapstone.product.GetAllProductResponse
import com.dicoding.projectcapstone.product.ProdRecFromMlResponse
import com.dicoding.projectcapstone.product.ProductDetailResponse
import com.dicoding.projectcapstone.profile.address.AddAddressResponse
import com.dicoding.projectcapstone.profile.address.AddAdressRequest
import com.dicoding.projectcapstone.profile.address.GetAddressResponse
import com.dicoding.projectcapstone.register.RegisterRequest
import retrofit2.http.POST
import com.dicoding.projectcapstone.register.RegisterResponse
import com.dicoding.projectcapstone.user.UserDataResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

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
    suspend fun getUserData(): UserDataResponse

//    Product
    @GET("product")
    suspend fun getAllProducts(): GetAllProductResponse

    @GET("product/recommendations")
    suspend fun getAllRecommendationProducts(): GetAllProductResponse

    @GET("product/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductDetailResponse

    @GET("product/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): GetAllProductResponse

    @POST("product/ml/recommendations")
    suspend fun getProductsRecomendationByMl(): ProdRecFromMlResponse

    //    Location
    @GET("locations")
    suspend fun getAllLocations(): List<LocationResponse>

    @GET("address/weather")
    suspend fun getWeather(): WeatherResponse

    @GET("address")
    suspend fun getAddress(): GetAddressResponse

    @POST("address/location")
    suspend fun addAddress(@Body request: AddAdressRequest): AddAddressResponse

    @PUT("address/location")
    suspend fun updateAddress(@Body request: AddAdressRequest): AddAddressResponse


}