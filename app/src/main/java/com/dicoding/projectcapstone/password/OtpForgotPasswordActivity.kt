package com.dicoding.projectcapstone.password

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.projectcapstone.R
import com.dicoding.projectcapstone.RetrofitClient
import com.dicoding.projectcapstone.utils.SessionManager
import com.dicoding.projectcapstone.databinding.ActivityOtpForgotPasswordBinding
import com.dicoding.projectcapstone.otp.OtpModel
import com.dicoding.projectcapstone.otp.OtpModelFactory
import com.dicoding.projectcapstone.repository.AuthRepository

class OtpForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpForgotPasswordBinding
    private lateinit var repository: AuthRepository
    private lateinit var sessionManager: SessionManager

    private val otpModel: OtpModel by viewModels {
        OtpModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = AuthRepository.getInstance(RetrofitClient.apiService)
        sessionManager = SessionManager(this)
        setupAction()
    }

    private fun setupAction() {
        binding.btnVerify.setOnClickListener {
            val intentOtpForgotPassword= Intent(this, ConfirmPasswordActivity::class.java)
            val otp_code = binding.etOtp.text.toString()

            if (binding.txtResendOtp.error == null) {
                if(otp_code == sessionManager.getOtpForgotPassword()) {
                    AlertDialog.Builder(this).apply {
                        setTitle("Yeah!")
                        setMessage("Otp benar")
                        setPositiveButton("Lanjut") { _, _ ->
                            startActivity(intentOtpForgotPassword)
                            finish()

                        }
                        create()
                        show()
                    }
                }
            } else {
                AlertDialog.Builder(this).apply {
                    setTitle("Oops!")
                    setMessage("Otp salah")
                    setPositiveButton("Ulangi", null)
                    create()
                    show()
                }
            }
        }

        binding.txtResendOtp.setOnClickListener {
            val email = sessionManager.getEmailForgotPassword()
            if (email != null) {
                otpModel.resendOtpForgotPassword(email) { otpResponse ->
                    if (otpResponse != null) {
                        if (otpResponse.success == true) {
                            otpResponse.result?.otp_code?.let { it1 ->
                                sessionManager.saveOtpForgotPassword(
                                    it1
                                )
                            }
                            AlertDialog.Builder(this).apply {
                                setTitle("OTP Sent")
                                setMessage("OTP has been resent to $email.")
                                setPositiveButton("Lanjut") { _, _ ->
                                    finish()
                                }
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
}