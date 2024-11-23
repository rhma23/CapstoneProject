package com.dicoding.projectcapstone.welcome

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.projectcapstone.R
import com.dicoding.projectcapstone.databinding.ActivityMainBinding
import com.dicoding.projectcapstone.databinding.ActivityWelcomeBinding
import com.dicoding.projectcapstone.role.RoleActivity

class WelcomeActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        binding.btnNext.setOnClickListener {
            val intent = Intent(this, RoleActivity::class.java)
            startActivity(intent)
        }
    }
}