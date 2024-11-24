package com.dicoding.projectcapstone.register

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.projectcapstone.RetrofitClient
import com.dicoding.projectcapstone.databinding.ActivityRegisterPembeliBinding

class RegisterPembeliActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterPembeliBinding
    private lateinit var repository: RegisterRepository

    private val registerModel: RegisterModel by viewModels {
        RegisterModelFactory(repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterPembeliBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = RegisterRepository.getInstance(RetrofitClient.apiService)
        setupView()
        setupAction()
        setupPasswordValidation()
        setupEmailValidation()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.btnregister.setOnClickListener {
            val username = binding.yourName.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val confirmPassword = binding.confirmPassword.text.toString()

            Log.d("setupAction", "setupAction: $username, $email, $password")

            if (binding.email.error == null && binding.password.error == null) {
                registerModel.register(username, email, password) { success ->
                    if (success) {
                        AlertDialog.Builder(this).apply {
                            setTitle("Yeah!")
                            setMessage("Akun dengan $email sudah jadi nih. Yuk, login dan belajar coding.")
                            setPositiveButton("Lanjut") { _, _ -> finish() }
                            create()
                            show()
                        }
                    } else {
                        binding.email.error = "Pendaftaran gagal. Coba lagi!"
                    }
                }
            }
        }
    }

    private fun setupPasswordValidation() {
        binding.password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null && s.length < 8) {
                    binding.password.error = "Password tidak boleh kurang dari 8 karakter"
                } else {
                    binding.password.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupEmailValidation() {
        binding.email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null && !Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                    binding.email.error = "Format email tidak valid"
                } else {
                    binding.email.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}