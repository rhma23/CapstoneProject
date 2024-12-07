package com.dicoding.projectcapstone.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.projectcapstone.utils.SessionManager
import com.dicoding.projectcapstone.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginModel(private val repository: AuthRepository) : ViewModel() {
    private lateinit var sessionManager: SessionManager

    fun setSessionManager(sessionManager: SessionManager) {
        this.sessionManager = sessionManager
    }

    fun login(email: String, password: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                Log.d("Login model 1", "Email: $email, Password: $password")
                val result = repository.login(email, password)
                if (result.success == true) {
                    result.result?.token?.let { sessionManager.saveToken(it) }
                    val userData = result.result?.token?.let { repository.getUserData() }
                    if (userData != null) {
                        if(userData.result?.is_verified == true) {
                            userData.result.id?.let { sessionManager.saveUserId(it) }
                            userData.result.username?.let { sessionManager.saveUsername(it) }
                            userData.result.email?.let { sessionManager.saveEmailUser(it) }
                            userData.result.role?.let { sessionManager.saveRole(it) }
                            userData.result.address?.let { sessionManager.saveAddressUser(it.toString()) }
                            sessionManager.saveIsLogin(true)
                            callback(result.success)
                        } else {
                            Log.d("C-Login Model F-login", "User belum register")
                            callback(false)
                        }
                        val address = repository.getAddres()
                        if (address != null) {
                            address.data?.address_name?.let { sessionManager.saveAddressUser(it) }
                        }
                        Log.d("Login Model", "login: $address")
                    }

                }
            } catch (e: Exception) {
                Log.e("Login", "Error: ${e.message}", e)
                callback(false)
            }
        }
    }
}
