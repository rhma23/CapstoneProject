package com.dicoding.projectcapstone.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.projectcapstone.SessionManager
import com.dicoding.projectcapstone.repository.AuthRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginModel (private val repository: AuthRepository) : ViewModel() {
    private lateinit var sessionManager: SessionManager
    fun login(email: String, password: String, onResult: (Boolean) -> Unit) {
        val TAG = "Login"
        Log.d(TAG, "Login: $email, $password")
        viewModelScope.launch {
            try {
                val response: LoginResponse = repository.login(email, password)
                if (response.success == true) {
                    Log.d(TAG, "Login successful: ${response.message}")
                    Log.d(TAG, "Login: ${response}")
                    response.token?.let { sessionManager.saveToken(it) }
                    onResult(true)
                } else {
                    Log.d(TAG, "Login failed: ${response.message}")
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