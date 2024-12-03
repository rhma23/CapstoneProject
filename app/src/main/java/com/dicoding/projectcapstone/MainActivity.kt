package com.dicoding.projectcapstone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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

        // Setup BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Set the default selected menu to "Home"
        bottomNavigationView.selectedItemId = R.id.home

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

        val btnFood = findViewById<Button>(R.id.btnFood)
        val btnDrink = findViewById<Button>(R.id.btnDrink)

        btnFood.setOnClickListener {
            val intent = Intent(this, KategoriMakananActivity::class.java)
            startActivity(intent)
        }

        btnDrink.setOnClickListener {
            val intent = Intent(this, KategoriMinumanActivity::class.java)
            startActivity(intent)
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
            events = listOf(),
            onItemClick = { dataItem ->
                Log.d("MainActivity", "Clicked item: ${dataItem.image?.let { helper.removePath(it) }}")
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
                productAdapter.updateData(it)
            } ?: run {
                Log.d("MainActivity", "Product list is null or empty")
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.addToBackStack(null) // Opsional: Tambahkan ke back stack untuk navigasi
        transaction.commit()
    }
}
