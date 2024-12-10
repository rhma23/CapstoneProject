package com.dicoding.projectcapstone.login

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.projectcapstone.MainActivity
import com.dicoding.projectcapstone.api.RetrofitClient
import com.dicoding.projectcapstone.databinding.ActivityLoginBinding
import com.dicoding.projectcapstone.password.ForgotPasswordActivity
import com.dicoding.projectcapstone.register.RegisterBuyerActivity
import com.dicoding.projectcapstone.repository.AuthRepository
import com.dicoding.projectcapstone.utils.SessionManager

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var repository: AuthRepository
    private lateinit var sessionManager: SessionManager

    private val loginModel: LoginModel by viewModels {
        LoginModelFactory(repository)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = AuthRepository.getInstance(RetrofitClient.apiService)
        sessionManager = SessionManager(this)
        loginModel.setSessionManager(sessionManager)

        val forgotPasswordTextView = binding.txtForgotPassword
        val signUpTextView = binding.txtSignUp

        // Membuat SpannableString untuk teks
        val spannableString = SpannableString("Forgot Password?")
        forgotPasswordTextView.text = spannableString

        val spannableStringSignUp = SpannableString("Sign Up")
        signUpTextView.text = spannableStringSignUp

        // Menambahkan efek saat disentuh
        binding.txtForgotPassword.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {

                    // Ubah warna teks menjadi warna custom (#4DA0C1) dan tambahkan underline
                    val spannableHover = SpannableString("Forgot Password?")
                    spannableHover.setSpan(
                        ForegroundColorSpan(Color.parseColor("#4DA0C1")),
                        0,
                        spannableHover.length,
                        SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    spannableHover.setSpan(
                        UnderlineSpan(),
                        0,
                        spannableHover.length,
                        SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    binding.txtForgotPassword.text = spannableHover
                }

                MotionEvent.ACTION_UP -> {
                    // Kembalikan teks ke tampilan awal (tanpa warna biru dan underline)
                    binding.txtForgotPassword.text = SpannableString("Forgot Password?")
                    binding.txtForgotPassword.performClick() // Panggil performClick untuk aksesibilitas

                    // Navigasi ke halaman Forgot Password
                    val intent = Intent(this, ForgotPasswordActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }

        binding.txtSignUp.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {

                    // Ubah warna teks menjadi warna custom (#4DA0C1) dan tambahkan underline
                    val spannableHover = SpannableString("Sign Up")
                    spannableHover.setSpan(
                        ForegroundColorSpan(Color.parseColor("#4DA0C1")),
                        0,
                        spannableHover.length,
                        SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    spannableHover.setSpan(
                        UnderlineSpan(),
                        0,
                        spannableHover.length,
                        SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    binding.txtSignUp.text = spannableHover
                }

                MotionEvent.ACTION_UP -> {
                    // Kembalikan teks ke tampilan awal (tanpa warna biru dan underline)
                    binding.txtSignUp.text = SpannableString("Sign Up")
                    binding.txtSignUp.performClick() // Panggil performClick untuk aksesibilitas

                    // Navigasi ke halaman Forgot Password
                    val intent = Intent(this, RegisterBuyerActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }

        setupAction()
    }

    private fun setupAction() {
        binding.txtSignUp.setOnClickListener {
            val intentRegister = Intent(this, RegisterBuyerActivity::class.java)
            startActivity(intentRegister)
        }

        binding.txtForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)

        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                AlertDialog.Builder(this).apply {
                    setTitle("Oops!")
                    setMessage("Incorrect email and password")
                    setPositiveButton("Repeat", null)
                    create()
                    show()
                }
            } else {
                binding.btnLogin.showLoading(true)
                binding.btnLogin.postDelayed({
                    loginModel.login(email, password) { success ->
                        binding.btnLogin.showLoading(false)

                        if (success) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            AlertDialog.Builder(this).apply {
                                setTitle("Oops!")
                                setMessage("Incorrect email and password")
                                setPositiveButton("Repeat", null)
                                create()
                                show()
                            }
                        }
                    }
                }, 2000)
            }
        }
    }
}
