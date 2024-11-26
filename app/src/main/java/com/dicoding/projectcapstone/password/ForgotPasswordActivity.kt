package com.dicoding.projectcapstone.password

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.projectcapstone.R
import com.dicoding.projectcapstone.RetrofitClient
import com.dicoding.projectcapstone.databinding.ActivityForgotPasswordBinding
import com.dicoding.projectcapstone.databinding.ActivityLoginBinding
import com.dicoding.projectcapstone.otp.OtpModel
import com.dicoding.projectcapstone.otp.OtpModelFactory
import com.dicoding.projectcapstone.repository.AuthRepository
import com.dicoding.projectcapstone.ui.MyButton
import com.dicoding.projectcapstone.utils.SessionManager

class ForgotPasswordActivity : AppCompatActivity() {
    lateinit var repository: AuthRepository
    private lateinit var sessionManager: SessionManager
    private lateinit var binding: ActivityForgotPasswordBinding

    private val otpModel: OtpModel by viewModels {
        OtpModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = AuthRepository.getInstance(RetrofitClient.apiService)
        sessionManager = SessionManager(this)

        binding.btnSubmitFgPassword.setOnClickListener {
            val intent = Intent(this, OtpForgotPasswordActivity::class.java)
            val email = binding.etForgotPassword.text.toString()
            if (email != null) {
                otpModel.resendOtpForgotPassword(email) { otpResponse ->
                    startActivity(intent)
                    if (otpResponse != null) {
                        val result = otpResponse.success
                        if (result == true) {
                            sessionManager.saveEmailForgotPassword(email)
                            otpResponse.result?.otp_code?.let { it1 ->
                                sessionManager.saveOtpForgotPassword(
                                    it1
                                )
                            }
                            AlertDialog.Builder(this).apply {
                                setTitle("OTP Sent")
                                setMessage("OTP has been resent to $email.")
                                setPositiveButton("Lanjut") { _, _ ->
                                    startActivity(intent)
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