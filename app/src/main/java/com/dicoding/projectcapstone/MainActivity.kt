package com.dicoding.projectcapstone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.projectcapstone.RetrofitClient.apiService
import com.dicoding.projectcapstone.databinding.ActivityMainBinding
import com.dicoding.projectcapstone.login.LoginActivity
import com.dicoding.projectcapstone.product.ProductAdapter
import com.dicoding.projectcapstone.product.ProductModel
import com.dicoding.projectcapstone.product.ProductRepository
import com.dicoding.projectcapstone.product.ProductViewModelFactory
import com.dicoding.projectcapstone.user.UserModel
import com.dicoding.projectcapstone.user.UserModelFactory
import com.dicoding.projectcapstone.user.UserRepository
import com.dicoding.projectcapstone.utils.Helper
import com.dicoding.projectcapstone.utils.SessionManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private var helper: Helper = Helper()
//    var helper: Helper = Helper()

    private lateinit var binding: ActivityMainBinding
    private lateinit var userRepository: UserRepository
    private lateinit var sessionManager: SessionManager

    // ViewModel untuk pengguna
    private val userModel: UserModel by viewModels {
        UserModelFactory(userRepository)
    }

    // ViewModel untuk produk
    private val productViewModel: ProductModel by viewModels {
        ProductViewModelFactory(ProductRepository(apiService))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi repositori dan sesi
        userRepository = UserRepository.getInstance(apiService)
        sessionManager = SessionManager(this)
        userModel.setSessionManager(sessionManager)

        // Cek apakah pengguna sudah login
        if (!sessionManager.getIsLogin()) {
            navigateToLogin()
        } else {
            val isHorizontal = true
            setupRecyclerView(isHorizontal)
            setupAction()
        }

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)

//        bottomNavigation.selectedItemId = R.id.home
//        bottomNavigation.selectedItemId = R.id.location
//        bottomNavigation.selectedItemId = R.id.profile

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.location -> {
                    startActivity(Intent(this, LokasiActivity::class.java))
                    true
                }
                R.id.profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun setupAction() {

        // Atur nama pengguna dari sesi
        binding.txtName.text = sessionManager.getUsername()

        // Tombol logout
        binding.button.setOnClickListener {
            userModel.logout()
            navigateToLogin()
        }
        // Memuat data produk
        productViewModel.fetchAllProducts()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setupRecyclerView(isHorizontal: Boolean) {
        // Initialize the adapter with an empty list and a click handler
        val productAdapter = ProductAdapter(
            events = listOf(), // Pass an empty list initially
            onItemClick = { dataItem ->
                // Handle item click here
                Log.d("MainActivity", "Clicked item: ${dataItem.image?.let { helper.removePath(it) }}")
                // Handle the item click
                Toast.makeText(this, "Clicked: ${dataItem.name}", Toast.LENGTH_SHORT).show()
            }
        )

        val layoutManager = if (isHorizontal) {
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        } else {
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }

        binding.rvRecommendations.apply {
            this.layoutManager = layoutManager
            adapter = productAdapter
            setHasFixedSize(true)
        }

        // Observe data and update the adapter
        productViewModel.products.observe(this) { productList ->
            productList?.let {
                productAdapter.updateData(it) // Update adapter's data
            } ?: run {
                Log.d("MainActivity", "Product list is null or empty")
            }
        }
    }


}
