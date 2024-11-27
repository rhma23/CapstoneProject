package com.dicoding.projectcapstone.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.projectcapstone.utils.SessionManager
import com.dicoding.projectcapstone.databinding.ActivityLoginBinding
import com.dicoding.projectcapstone.MainActivity
import com.dicoding.projectcapstone.R
import com.dicoding.projectcapstone.RetrofitClient
import com.dicoding.projectcapstone.otp.OtpActivity
import com.dicoding.projectcapstone.password.ForgotPasswordActivity
import com.dicoding.projectcapstone.register.RegisterBuyerActivity
import com.dicoding.projectcapstone.repository.AuthRepository
import com.dicoding.projectcapstone.ui.MyButton

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var repository: AuthRepository
    private lateinit var sessionManager: SessionManager
    private lateinit var myButton: MyButton
    private val loginModel: LoginModel by viewModels {
        LoginModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = AuthRepository.getInstance(RetrofitClient.apiService)
        sessionManager = SessionManager(this)
        myButton = findViewById(R.id.btnLogin) // Initialize myButton
        loginModel.setSessionManager(sessionManager) // Pass sessionManager to loginModel
        myButton.isPressed = false
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
            myButton.isPressed = true
            val intent = Intent(this, LoginActivity::class.java)
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                myButton.isPressed = false
                AlertDialog.Builder(this).apply {
                    setTitle("Opps!")
                    setMessage("Email dan password salah")
                    setPositiveButton("Ulangi") { _, _ ->
                        finish()
                        startActivity(intent)
                        finish()
                    }
                    create()
                    show()
                }
            } else {
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
}