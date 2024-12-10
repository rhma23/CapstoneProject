package com.dicoding.projectcapstone.profile

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dicoding.projectcapstone.MainActivity
import com.dicoding.projectcapstone.R
import com.dicoding.projectcapstone.api.RetrofitClient
import com.dicoding.projectcapstone.databinding.ActivityProfileBinding
import com.dicoding.projectcapstone.location.LokasiActivity
import com.dicoding.projectcapstone.login.LoginActivity
import com.dicoding.projectcapstone.user.UserModel
import com.dicoding.projectcapstone.user.UserModelFactory
import com.dicoding.projectcapstone.user.UserRepository
import com.dicoding.projectcapstone.utils.SessionManager

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var userRepository: UserRepository
    private lateinit var sessionManager: SessionManager

    private val userModel: UserModel by lazy {
        val apiService = RetrofitClient.apiService
        userRepository = UserRepository(apiService)

        // Create the ViewModel with the repository
        val viewModelFactory = UserModelFactory(userRepository)
        viewModelFactory.create(UserModel::class.java).apply {
            // Explicitly set the SessionManager
            setSessionManager(SessionManager(this@ProfileActivity))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SessionManager(this)
        binding.profileName.text = sessionManager.getUsername()
        // Navigasi ke fragment edit profil atau edit alamat
        binding.editProfile.setOnClickListener {
            navigateToFragment(ShowProfileFragment())
        }

        // Navigasi menggunakan bottom navigation
        val bottomNavigationView = binding.bottomNavigation
        bottomNavigationView.selectedItemId = R.id.profile
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    navigateWithLoading(MainActivity::class.java)
                    true
                }
                R.id.location -> {
                    navigateWithLoading(LokasiActivity::class.java)
                    true
                }
                R.id.profile -> {
                    navigateWithLoading(ProfileActivity::class.java)
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
        navigateWithLoading(LoginActivity::class.java)
        finish()
    }

    private fun navigateWithLoading(targetActivity: Class<*>) {
        Log.d("ProfileActivity", "Loading started")
        binding.loadingProfile.visibility = View.VISIBLE // Tampilkan ProgressBar
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, targetActivity))
            binding.loadingProfile.visibility = View.GONE // Sembunyikan ProgressBar
        }, 1500)
    }

    private fun navigateToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
