package com.dicoding.projectcapstone.password

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.projectcapstone.api.RetrofitClient
import com.dicoding.projectcapstone.databinding.ActivityNewPasswordBinding
import com.dicoding.projectcapstone.login.LoginActivity
import com.dicoding.projectcapstone.otp.OtpModel
import com.dicoding.projectcapstone.otp.OtpModelFactory
import com.dicoding.projectcapstone.repository.AuthRepository
import com.dicoding.projectcapstone.utils.SessionManager

class NewPasswordActivity : AppCompatActivity() {
    lateinit var repository: AuthRepository
    private lateinit var sessionManager: SessionManager
    private lateinit var binding: ActivityNewPasswordBinding

    private val otpModel: OtpModel by viewModels {
        OtpModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = AuthRepository.getInstance(RetrofitClient.apiService)
        sessionManager = SessionManager(this)
        otpModel.setSessionManager(sessionManager)

        setupAction()
    }

    private fun setupAction() {
        binding.btnSubmitNewPass.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            val newPassword = binding.etNewPassword.text.toString()
            val email = sessionManager.getEmailForgotPassword()
            val otp_code = sessionManager.getOtpForgotPassword()
            Log.d("setupAction New Password", "setupAction: $email, $otp_code, $newPassword")
//            if (newPassword != null && email != null && otp_code != null)
            if (email != null && otp_code != null) {
                otpModel.resetPassowrd(otp_code, email, newPassword) { success ->
                    if (success == true) {
                        AlertDialog.Builder(this).apply {
                            setTitle("Success")
                            setMessage("Password has been successfully changed")
                            setPositiveButton("Login") { _, _ ->
                                startActivity(intent)
                                finish()
                            }
                            create()
                            show()
                        }
                    } else {
                        AlertDialog.Builder(this).apply {
                            setTitle("Failed")
                            setMessage("Failed to change password. Please try again")
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