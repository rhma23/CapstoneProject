package com.dicoding.projectcapstone.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.projectcapstone.SessionManager
import com.dicoding.projectcapstone.databinding.ActivityLoginBinding
import com.dicoding.projectcapstone.MainActivity
import com.dicoding.projectcapstone.RetrofitClient
import com.dicoding.projectcapstone.repository.AuthRepository

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var repository: AuthRepository
    private lateinit var sessionManager: SessionManager

    private val loginModel: LoginModel by viewModels {
        LoginModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = AuthRepository.getInstance(RetrofitClient.apiService)
        sessionManager = SessionManager(this)
        loginModel.setSessionManager(sessionManager) // Pass sessionManager to loginModel
        setupAction()
    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            loginModel.login(email, password) { success ->
                if (success) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}