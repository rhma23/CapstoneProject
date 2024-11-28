package com.dicoding.projectcapstone.otp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.projectcapstone.RetrofitClient
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        repository = AuthRepository.getInstance(RetrofitClient.apiService)
        sessionManager = SessionManager(this)
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
                            setMessage("Akun dengan $email sudah jadi nih. Yuk, login segera")
                            setPositiveButton("Lanjut") { _, _ ->
                                startActivity(intentLogin)
                                finish()
                            }
                            create()
                            show()
                        }
                    } else {
                        AlertDialog.Builder(this).apply {
                            setTitle("Oops, verivkasi gagal!")
                            setMessage("Akun dengan $email gagal verifikasi. Coba lagi ya.")
                            setPositiveButton("Ulangi") { _, _ -> finish()
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