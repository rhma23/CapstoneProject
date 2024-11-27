package com.dicoding.projectcapstone.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    companion object {
        private const val EMAIL_KEY = "email"
        private const val TOKEN_KEY = "token"
        private const val EMAIL_FORGOT_PASSW_KEY = "email_forgot_password"
        private const val OTP_FORGOT_PASSWORD_KEY = "otp_forgot_password"
    }

    fun saveEmail(email: String) {
        val editor = prefs.edit()
        editor.putString(EMAIL_KEY, email)
        editor.apply()
    }

    fun getEmail(): String? {
        return prefs.getString(EMAIL_KEY, null)
    }

    fun saveEmailForgotPassword(email: String) {
        val editor = prefs.edit()
    editor.putString(EMAIL_FORGOT_PASSW_KEY, email)
        editor.apply()
    }

    fun getEmailForgotPassword(): String? {
        return prefs.getString(EMAIL_FORGOT_PASSW_KEY, null)
    }

    fun saveOtpForgotPassword(otp: String) {
        val editor = prefs.edit()
        editor.putString(OTP_FORGOT_PASSWORD_KEY, otp)
        editor.apply()
    }

    fun getOtpForgotPassword(): String? {
        return prefs.getString(OTP_FORGOT_PASSWORD_KEY, null)
    }

    fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString(TOKEN_KEY, token)
        editor.apply()
    }

    fun getToken(): String? {
        return prefs.getString(TOKEN_KEY, null)
    }

    fun clearSession() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}