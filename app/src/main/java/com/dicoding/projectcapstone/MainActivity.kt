package com.dicoding.projectcapstone

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.dicoding.projectcapstone.databinding.ActivityMainBinding
import com.dicoding.projectcapstone.login.LoginActivity
import com.dicoding.projectcapstone.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sessionManager = SessionManager(this)
        if (sessionManager.getToken() == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            setupAction()
        }
    }

    private fun setupAction() {
        binding.btnWelcome.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
        }
    }
}
