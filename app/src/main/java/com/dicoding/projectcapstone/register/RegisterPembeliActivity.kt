package com.dicoding.projectcapstone.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.projectcapstone.RetrofitClient
import com.dicoding.projectcapstone.SessionManager
import com.dicoding.projectcapstone.databinding.ActivityRegisterPembeliBinding
import com.dicoding.projectcapstone.otp.OtpActivity

class RegisterPembeliActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterPembeliBinding
    private lateinit var repository: RegisterRepository
    private lateinit var sessionManager: SessionManager

    private val registerModel: RegisterModel by viewModels {
        RegisterModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterPembeliBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = RegisterRepository.getInstance(RetrofitClient.apiService)
        sessionManager = SessionManager(this)
        setupView()
        setupAction()
    }

    private fun setupView() {
        // Existing setupView code
    }

    private fun setupAction() {
        binding.btnregister.setOnClickListener {
            val username = binding.yourName.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val confirmPassword = binding.confirmPassword.text.toString()
            val role = "buyer"

            if (binding.email.error == null && binding.password.error == null) {
                registerModel.register(username, email, password, role) { success ->
                    if (success) {
                        sessionManager.saveEmail(email)
                        val intent = Intent(this, OtpActivity::class.java)
                        AlertDialog.Builder(this).apply {
                            setTitle("Yeah!")
                            setMessage("Akun dengan $email sebentar lagi jadi nihh. Yuk, lanjutkan dengan verifikasi email")
                            setPositiveButton("Lanjut") { _, _ ->
                                finish()
                                startActivity(intent)
                            }
                            create()
                            show()
                        }

                    } else {
                        AlertDialog.Builder(this).apply {
                            setTitle("Oops!")
                            setMessage("Akun dengan $email gagal dibuat. Coba lagi ya.")
                            setPositiveButton("Ulangi") { _, _ -> finish() }
                            create()
                            show()
                        }
                    }
                }
            }
        }
    }
}