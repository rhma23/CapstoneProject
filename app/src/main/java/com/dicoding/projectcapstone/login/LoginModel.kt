package com.dicoding.projectcapstone.login

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
            val result = repository.login(email, password)
            if (result.success == true) {
                val userData = result.result?.token?.let { repository.getUserData(it) }
                if(userData != null && userData.success == true && userData.result?.isVerified == true) {
                    userData.result.id?.let { sessionManager.saveUserId(it) }
                    userData.result.username?.let { sessionManager.saveUsername(it) }
                    userData.result.email?.let { sessionManager.saveEmailUser(it) }
                    userData.result.role?.let { sessionManager.saveRole(it) }
                    sessionManager.saveIsLogin(true)
                }
            }
            callback(result.success == true)
        }
    }
}
