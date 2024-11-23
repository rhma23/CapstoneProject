package com.dicoding.projectcapstone.register

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.projectcapstone.R
import com.dicoding.projectcapstone.databinding.ActivityRegisterPedagangBinding
import com.dicoding.projectcapstone.databinding.ActivityRegisterPembeliBinding

class RegisterPembeliActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterPembeliBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_register_pembeli)
    }
}