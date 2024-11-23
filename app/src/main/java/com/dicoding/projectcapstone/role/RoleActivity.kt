package com.dicoding.projectcapstone.role

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.projectcapstone.databinding.ActivityRoleBinding
import com.dicoding.projectcapstone.register.RegisterPedagangActivity
import com.dicoding.projectcapstone.register.RegisterPembeliActivity

class RoleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRoleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        binding.btnPembeli.setOnClickListener {
            val intent = Intent(this, RegisterPembeliActivity::class.java)
            startActivity(intent)
        }

        binding.btnPedagang.setOnClickListener {
            val intent = Intent(this, RegisterPedagangActivity::class.java)
            startActivity(intent)
        }
    }
}