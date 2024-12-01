package com.dicoding.projectcapstone.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.projectcapstone.API.RetrofitClient
import com.dicoding.projectcapstone.utils.SessionManager
import com.dicoding.projectcapstone.databinding.ActivityRegisterBuyerBinding
import com.dicoding.projectcapstone.login.LoginActivity
import com.dicoding.projectcapstone.otp.OtpRegisterActivity
import com.dicoding.projectcapstone.repository.AuthRepository

class RegisterBuyerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBuyerBinding
    private lateinit var repository: AuthRepository
    private lateinit var sessionManager: SessionManager

    private val registerModel: RegisterModel by viewModels {
        RegisterModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBuyerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = AuthRepository.getInstance(RetrofitClient.apiService)
        sessionManager = SessionManager(this)

        setupView()
        setupAction()
        setupPasswordValidation()
        setupEmailValidation()
    }

    private fun setupView() {
    }

    private fun setupAction() {
        binding.txtLogin.setOnClickListener {
            val intentLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentLogin)
        }

        binding.btnRegister.setOnClickListener {
            val intentRegister = Intent(this, RegisterBuyerActivity::class.java)
            val username = binding.etYourName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()
            val role = "buyer"
            if (password == confirmPassword) {
                if (binding.etEmail.error == null && binding.etPassword.error == null) {
                    registerModel.register(username, email, password, role) { success ->
                        if (!isFinishing && !isDestroyed) {
                            if (success) {
                                sessionManager.saveEmail(email)
                                AlertDialog.Builder(this).apply {
                                    setTitle("Yeah!")
                                    setMessage("Akun dengan $email sudah jadi nih. Yuk, login dan belajar coding.")
                                    setPositiveButton("Lanjut") { _, _ ->
                                        finish()
                                        val intent = Intent(
                                            this@RegisterBuyerActivity,
                                            OtpRegisterActivity::class.java
                                        )
                                        startActivity(intent)
                                    }
                                    create()
                                    show()
                                }
                            } else {
                                AlertDialog.Builder(this).apply {
                                    setTitle("Oops!")
                                    setMessage("Akun dengan $email gagal dibuat. Coba lagi ya.")
                                    setPositiveButton("Ulangi") { _, _ ->
                                        startActivity(intentRegister)
                                        finish()
                                    }
                                    create()
                                    show()
                                }
                            }
                        }
                    }
                }
            } else {
                binding.etConfirmPassword.error = "Password tidak sama"
            }
            binding.btnRegister.setOnClickListener {
                val intentRegister = Intent(this, RegisterBuyerActivity::class.java)
                val username = binding.etYourName.text.toString()
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                val confirmPassword = binding.etConfirmPassword.text.toString()
                val role = "buyer"
                if (password == confirmPassword) {
                    if (binding.etEmail.error == null && binding.etPassword.error == null) {
                        registerModel.register(username, email, password, role) { success ->
                            if (!isFinishing && !isDestroyed) {
                                if (success) {
                                    sessionManager.saveEmail(email)
                                    AlertDialog.Builder(this).apply {
                                        setTitle("Yeah!")
                                        setMessage("The account with $email has been successfully created. Please log in to continue.")
                                        setPositiveButton("Continue") { _, _ ->
                                            finish()
                                            val intent = Intent(
                                                this@RegisterBuyerActivity,
                                                OtpRegisterActivity::class.java
                                            )
                                            startActivity(intent)
                                        }
                                        create()
                                        show()
                                    }
                                } else {
                                    AlertDialog.Builder(this).apply {
                                        setTitle("Oops!")
                                        setMessage("The account with $email failed to be created. Please try again.")
                                        setPositiveButton("Retry") { _, _ ->
                                            startActivity(intentRegister)
                                            finish()
                                        }
                                        create()
                                        show()
                                    }
                                }
                            }
                        }
                    }
                } else {
                    binding.etConfirmPassword.error = "Password is not the same"
                }
            }
        }
    }

    private fun setupEmailValidation() {
        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null && !Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                    binding.etEmail.error = "Invalid email format"
                } else {
                    binding.etEmail.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupPasswordValidation() {
        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null && s.length < 8) {
                    binding.etPassword.error = "Password cannot be less than 8 characters"
                } else {
                    binding.etPassword.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}
