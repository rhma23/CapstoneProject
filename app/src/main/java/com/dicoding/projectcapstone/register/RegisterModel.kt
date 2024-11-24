package com.dicoding.projectcapstone.register

import kotlinx.coroutines.launch


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import retrofit2.HttpException

class RegisterModel (private val repository: RegisterRepository) : ViewModel() {

    fun register(username: String, email: String, password: String, onResult: (Boolean) -> Unit) {
        val TAG = "registerUser"
        Log.d(TAG, "registerUser: $username, $email, $password")
        viewModelScope.launch {
            try {
                val response: RegisterResponse = repository.register(username, email, password)
                if (response.success == true) {
                    Log.d(TAG, "Registration successful: ${response.message}")
                    Log.d(TAG, "register: ${response.result}")
                    onResult(true)
                } else {
                    Log.d(TAG, "Registration failed: ${response.message}")
                    onResult(false)
                }
            } catch (e: HttpException) {
                Log.e(TAG, "HTTP Error: ${e.code()} - ${e.response()?.errorBody()?.string()}")
                onResult(false)
            } catch (e: Exception) {
                Log.e(TAG, "Unexpected Error", e)
                onResult(false)
            }
        }

    }
}
