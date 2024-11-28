package com.dicoding.projectcapstone.otp

import kotlinx.coroutines.launch
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.projectcapstone.password.ForgotPasswordResponse
import com.dicoding.projectcapstone.repository.AuthRepository
import com.dicoding.projectcapstone.utils.SessionManager
import retrofit2.HttpException

class OtpModel(private val repository: AuthRepository) : ViewModel() {

    fun verify(email: String, otp_code: String, onResult: (Boolean) -> Unit) {
        Log.d("Otp", "Otp: $email, $otp_code")
        viewModelScope.launch {
            try {
                val response: OtpResponse = repository.verify(email, otp_code)
                if (response.success == true) {
                    Log.d("Otp", "Otp verification successful: ${response.message}")
                    Log.d("Otp", "Otp verification result: ${response.result}")
                    onResult(true)
                } else {
                    Log.d("Otp", "Otp verification failed: ${response.message}")
                    onResult(false)
                }
            } catch (e: HttpException) {
                Log.e("Otp", "HTTP Error: ${e.code()} - ${e.response()?.errorBody()?.string()}")
                onResult(false)
            } catch (e: Exception) {
                Log.e("Otp", "Unexpected Error", e)
                onResult(false)
            }
        }
    }

    fun resendOtp(email: String, onResult: (Boolean) -> Unit) {
        Log.d("Otp", "Otp: $email")
        viewModelScope.launch {
            try {
                val response: OtpResponse = repository.resendOtp(email)
                if (response.success == true) {
                    Log.d("Otp", "Resend Otp successful: ${response.message}")
                    Log.d("Otp", "Resend Otp result: ${response.result}")
                    onResult(true)
                } else {
                    Log.d("Otp", "Resend Otp failed: ${response.message}")
                    onResult(false)
                }
            } catch (e: HttpException) {
                Log.e("Otp", "HTTP Error: ${e.code()} - ${e.response()?.errorBody()?.string()}")
                onResult(false)
            } catch (e: Exception) {
                Log.e("Otp", "Unexpected Error", e)
                onResult(false)
            }
        }
    }

    fun resendOtpForgotPassword(email: String, onResult: (OtpResponse?) -> Unit) {
        Log.d("Otp", "Otp: $email")
        viewModelScope.launch {
            try {
                val response: OtpResponse = repository.resendOtp(email)
                if (response.success == true) {
                    Log.d("Otp", "Resend Otp successful: ${response.message}")
                    Log.d("Otp", "Resend Otp result: ${response.result}")
                    onResult(response)
                } else {
                    Log.d("Otp", "Resend Otp failed: ${response.message}")
                    onResult(response)
                }
            } catch (e: HttpException) {
                Log.e("Otp", "HTTP Error: ${e.code()} - ${e.response()?.errorBody()?.string()}")
                onResult(null)
            } catch (e: Exception) {
                Log.e("Otp", "Unexpected Error", e)
                onResult(null)
            }
        }
    }

    fun resetPassowrd(otp_code: String, email: String, newPasword: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response: ForgotPasswordResponse = repository.resetPassword(otp_code, email, newPasword)
                if (response.success == true) {
                    Log.d("Reset Passoword", "Reset Passoword successful: ${response.message}")
                    Log.d("Reset Passoword", "Reset Passoword result: ${response}")
                    onResult(true)
                } else {
                    Log.d("Reset Passoword", "Reset Passoword failed: ${response.message}")
                    onResult(false)
                }
            } catch (e: HttpException) {
                Log.e("Reset Passoword", "HTTP Error: ${e.code()} - ${e.response()?.errorBody()?.string()}")
                onResult(false)
            } catch (e: Exception) {
                Log.e("Reset Passoword", "Unexpected Error", e)
                onResult(false)
            }
        }
    }
}