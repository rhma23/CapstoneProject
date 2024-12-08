package com.dicoding.projectcapstone.otp

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
import com.dicoding.projectcapstone.api.RetrofitClient
import com.dicoding.projectcapstone.utils.SessionManager
import com.dicoding.projectcapstone.databinding.ActivityOtpRegisterBinding
import com.dicoding.projectcapstone.login.LoginActivity
import com.dicoding.projectcapstone.repository.AuthRepository

class OtpRegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpRegisterBinding
    private lateinit var repository: AuthRepository
    private lateinit var sessionManager: SessionManager

    private val otpModel: OtpModel by viewModels {
        OtpModelFactory(repository)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = AuthRepository.getInstance(RetrofitClient.apiService)
        sessionManager = SessionManager(this)

        setupSpannableText()
        setupAction()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupSpannableText() {
        val resendOtpTextView = binding.txtResendOtp

        // Membuat SpannableString untuk teks "Resend again"
        val spannableStringSignUp = SpannableString("Resend again")
        resendOtpTextView.text = spannableStringSignUp

        // Menambahkan efek hover pada teks "Resend again"
        resendOtpTextView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    val spannableHover = SpannableString("Resend again")
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
                    resendOtpTextView.text = spannableHover
                }

                MotionEvent.ACTION_UP -> {
                    resendOtpTextView.text = SpannableString("Resend again")
                    resendOtpTextView.performClick()
                }
            }
            true
        }
    }

    private fun setupAction() {
        binding.btnVerify.setOnClickListener {
            val email = sessionManager.getEmail()
            val otp_code = "${binding.etBox1.text}${binding.etBox2.text}${binding.etBox3.text}" +
                    "${binding.etBox4.text}${binding.etBox5.text}${binding.etBox6.text}"

            if (email != null && otp_code.length == 6) {
                binding.btnVerify.showLoading(true)
                otpModel.verify(email, otp_code) { success ->

                    binding.btnVerify.postDelayed({
                        binding.btnVerify.showLoading(false)
                        if (success) {
                            AlertDialog.Builder(this).apply {
                                setTitle("Success!")
                                setMessage("The account with $email has been successfully created. Please log in to continue.")
                                setPositiveButton("Continue") { _, _ ->
                                    startActivity(
                                        Intent(
                                            this@OtpRegisterActivity,
                                            LoginActivity::class.java
                                        )
                                    )
                                    finish()
                                }
                                create()
                                show()
                            }
                        } else {
                            showErrorDialog("Verification failed! Please try again.")
                        }
                    }, 2000)
                }
            } else {
                showErrorDialog("Please enter a valid 6-digit OTP code.")
            }
        }

        binding.txtResendOtp.setOnClickListener {
            val email = sessionManager.getEmail()
            if (email != null) {
                binding.txtResendOtp.isEnabled = false
                otpModel.resendOtp(email) { success ->
                    binding.txtResendOtp.postDelayed({
                        binding.txtResendOtp.isEnabled = true
                        if (success) {
                            AlertDialog.Builder(this).apply {
                                setTitle("OTP Resent")
                                setMessage("A new OTP code has been sent to $email.")
                                setPositiveButton("OK", null)
                                create()
                                show()
                            }
                        } else {
                            showErrorDialog("Failed to resend OTP. Please try again.")
                        }
                    }, 2000)
                }
            } else {
                showErrorDialog("Email is not available. Please login again.")
            }
        }
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle("Error")
            setMessage(message)
            setPositiveButton("OK", null)
            create()
            show()
        }
    }
}