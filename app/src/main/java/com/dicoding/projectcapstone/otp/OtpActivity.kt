package com.dicoding.projectcapstone.otp

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.projectcapstone.RetrofitClient
import com.dicoding.projectcapstone.SessionManager
import com.dicoding.projectcapstone.databinding.ActivityOtpBinding

class OtpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpBinding
    private lateinit var repository: OtpRepository
    private lateinit var sessionManager: SessionManager

    private val otpModel: OtpModel by viewModels {
        OtpModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = OtpRepository.getInstance(RetrofitClient.apiService)
        sessionManager = SessionManager(this)
        setupAction()
    }

    private fun setupAction() {
        binding.btnregister.setOnClickListener {
            val email = sessionManager.getEmail()
            val otp_code = binding.otp.text.toString()
            Log.d("setupAction", "setupAction: $email, $otp_code")

            if (email != null && binding.otp.error == null) {
                otpModel.verify(email, otp_code) { success ->
                    if (success) {
                        AlertDialog.Builder(this).apply {
                            setTitle("Yeah!")
                            setMessage("Akun dengan $email sudah jadi nih. Yuk, login segera")
                            setPositiveButton("Lanjut") { _, _ -> finish() }
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

        binding.btnResendOtp.setOnClickListener {
            val email = sessionManager.getEmail()
            if (email != null) {
                otpModel.resendOtp(email) { success ->
                    if (success) {
                        AlertDialog.Builder(this).apply {
                            setTitle("OTP Sent")
                            setMessage("OTP has been resent to $email.")
                            setPositiveButton("OK", null)
                            create()
                            show()
                        }
                    } else {
                        AlertDialog.Builder(this).apply {
                            setTitle("Error")
                            setMessage("Failed to resend OTP. Please try again.")
                            setPositiveButton("Retry", null)
                            create()
                            show()
                        }
                    }
                }
            }
        }
    }
}