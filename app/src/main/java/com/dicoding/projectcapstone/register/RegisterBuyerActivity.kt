package com.dicoding.projectcapstone.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.projectcapstone.R
import com.dicoding.projectcapstone.databinding.ActivityRegisterPembeliBinding

class RegisterBuyerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterPembeliBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_register_pembeli)
    }
}