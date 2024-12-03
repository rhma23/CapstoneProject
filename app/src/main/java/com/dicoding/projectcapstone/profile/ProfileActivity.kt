package com.dicoding.projectcapstone.profile

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dicoding.projectcapstone.MainActivity
import com.dicoding.projectcapstone.R
import com.dicoding.projectcapstone.databinding.ActivityProfileBinding
import com.dicoding.projectcapstone.login.LoginActivity
import com.dicoding.projectcapstone.ui.kategori.LokasiActivity
import com.dicoding.projectcapstone.user.UserModel
import com.dicoding.projectcapstone.user.UserModelFactory
import com.dicoding.projectcapstone.user.UserRepository
import com.google.android.material.bottomnavigation.BottomNavigationView


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var userRepository: UserRepository

    private val userModel: UserModel by viewModels {
        UserModelFactory(userRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_profile)

        findViewById<LinearLayout>(R.id.edit_profile).setOnClickListener {
            navigateToFragment(EditProfileFragment())
        }

        findViewById<LinearLayout>(R.id.address).setOnClickListener {
            navigateToFragment(EditAddressFragment())
        }

        // Setup BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Set the default selected menu to "Profile"
        bottomNavigationView.selectedItemId = R.id.profile

        // Listener untuk navigasi
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.location -> {
                    val intent = Intent(this, LokasiActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }
        setupAction()
    }

    private fun setupAction() {
        // Tombol logout
        binding.logout.setOnClickListener {
            userModel.logout()
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    // Fungsi untuk berpindah fragment
    private fun navigateToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}

