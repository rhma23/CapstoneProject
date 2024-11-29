package com.dicoding.projectcapstone.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.projectcapstone.RetrofitClient
import com.dicoding.projectcapstone.utils.SessionManager
import com.dicoding.projectcapstone.databinding.ActivityRegisterBuyerBinding
import com.dicoding.projectcapstone.otp.OtpRegisterActivity
import com.dicoding.projectcapstone.repository.AuthRepository
import com.dicoding.projectcapstone.ui.ButtonRegist

class RegisterBuyerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBuyerBinding
    private lateinit var repository: AuthRepository
    private lateinit var sessionManager: SessionManager
    private lateinit var buttonRegist: ButtonRegist

    private val registerModel: RegisterModel by viewModels {
        RegisterModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBuyerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = AuthRepository.getInstance(RetrofitClient.apiService)
        sessionManager = SessionManager(this)

        buttonRegist = binding.btnRegister

        setupView()
        setupAction()
    }

    private fun setupView() {
    }

    private fun setupAction() {

        buttonRegist.setOnClickListener {
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
                                        val intent = Intent(this@RegisterBuyerActivity, OtpRegisterActivity::class.java)
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
                                        finish() }
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
        }
    }
}