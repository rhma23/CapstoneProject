package com.dicoding.projectcapstone.otp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.projectcapstone.RetrofitClient

import com.dicoding.projectcapstone.utils.SessionManager
import com.dicoding.projectcapstone.databinding.ActivityOtpRegisterBinding
import com.dicoding.projectcapstone.login.LoginActivity
import com.dicoding.projectcapstone.password.NewPasswordActivity
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

        val resendOtpTextView = binding.txtResendOtp

        // Membuat SpannableString untuk teks
        val spannableStringSignUp = SpannableString("Resend again")
        resendOtpTextView.text = spannableStringSignUp

        binding.txtResendOtp.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {

                    // Ubah warna teks menjadi warna custom (#4DA0C1) dan tambahkan underline
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
                    binding.txtResendOtp.text = spannableHover
                }
                MotionEvent.ACTION_UP -> {
                    // Kembalikan teks ke tampilan awal (tanpa warna biru dan underline)
                    binding.txtResendOtp.text = SpannableString("Resend again")
                    binding.txtResendOtp.performClick() // Panggil performClick untuk aksesibilitas

                    // Navigasi ke halaman Login
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }

        setupAction()
    }

    private fun setupAction() {
        binding.btnVerify.setOnClickListener {
            val intentOtp = Intent(this, OtpRegisterActivity::class.java)
            val intentLogin = Intent(this, LoginActivity::class.java)
            val email = sessionManager.getEmail()
            val otp_code = binding.etOtp.text.toString()
            Log.d("setupAction", "setupAction: $email, $otp_code")

            if (email != null && binding.etOtp.error == null) {
                otpModel.verify(email, otp_code) { success ->
                    if (success) {
                        AlertDialog.Builder(this).apply {
                            setTitle("Yeah!")
                            setMessage("The account with $email has been successfully created. Please log in to continue.")
                            setPositiveButton("Continue") { _, _ ->
                                startActivity(intentLogin)
                                finish()
                            }
                            create()
                            show()
                        }
                    } else {
                        AlertDialog.Builder(this).apply {
                            setTitle("Oops, verification failed!")
                            setMessage("Account with $email failed verification. Please try again.")
                            setPositiveButton("Retry") { _, _ -> finish()
                                startActivity(intentOtp)
                                finish()
                            }
                            create()
                            show()
                        }
                    }
                }
            }
        }

        binding.txtResendOtp.setOnClickListener {
            val email = sessionManager.getEmail()
            if (email != null) {
                otpModel.resendOtp(email) { success ->
                    if (success) {
                        AlertDialog.Builder(this).apply {
                            setTitle("OTP Dikirim Ulang")
                            setMessage("OTP telah dikirim ulang ke $email.")
                            setPositiveButton("OK", null)
                            create()
                            show()
                        }
                    } else {
                        AlertDialog.Builder(this).apply {
                            setTitle("Gagal Mengirim Ulang OTP")
                            setMessage("Gagal mengirim ulang OTP. Coba lagi.")
                            setPositiveButton("Coba Lagi", null)
                            create()
                            show()
                        }
                    }
                }
            }
        }
    }
}