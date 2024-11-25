package com.dicoding.projectcapstone

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    companion object {
        private const val EMAIL_KEY = "email"
    }

    fun saveEmail(email: String) {
        val editor = prefs.edit()
        editor.putString(EMAIL_KEY, email)
        editor.apply()
    }

    fun getEmail(): String? {
        return prefs.getString(EMAIL_KEY, null)
    }

    fun clearSession() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}