package com.dicoding.projectcapstone.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dicoding.projectcapstone.SessionManager
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
                result.token?.let { sessionManager.saveToken(it) }
            }
            callback(result.success == true)
        }
    }
}
