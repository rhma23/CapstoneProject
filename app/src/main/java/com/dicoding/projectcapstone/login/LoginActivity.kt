package com.dicoding.projectcapstone.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.projectcapstone.utils.SessionManager
import com.dicoding.projectcapstone.databinding.ActivityLoginBinding
import com.dicoding.projectcapstone.MainActivity
import com.dicoding.projectcapstone.API.RetrofitClient
import com.dicoding.projectcapstone.password.ForgotPasswordActivity
import com.dicoding.projectcapstone.register.RegisterBuyerActivity
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
        binding.txtSignUp.setOnClickListener {
            val intentRegister = Intent(this, RegisterBuyerActivity::class.java)
            startActivity(intentRegister)
        }

        binding.txtForgotPassword.setOnClickListener {
            val intentForgotPasword = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intentForgotPasword)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            Log.d("setupAction Login 2", "setupAction: $email, $password")
            if (email.isEmpty() || password.isEmpty()) {
                AlertDialog.Builder(this).apply {
                    setTitle("Opps!")
                    setMessage("Email dan password salah")
                    setPositiveButton("Ulangi", null)
                    create()
                    show()
                }
            } else {
                Log.d("setupAction Login 1", "setupAction: $email, $password")
                loginModel.login(email, password) { success ->
                    if (success) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        AlertDialog.Builder(this).apply {
                            setTitle("Opps!")
                            setMessage("Email dan password salah")
                            setPositiveButton("Ulangi", null)
                            create()
                            show()
                        }
                    }
                }
            }
        }
    }
}