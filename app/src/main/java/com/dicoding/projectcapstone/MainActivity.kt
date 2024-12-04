package com.dicoding.projectcapstone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.projectcapstone.API.RetrofitClient.apiService
import com.dicoding.projectcapstone.databinding.ActivityMainBinding
import com.dicoding.projectcapstone.login.LoginActivity
import com.dicoding.projectcapstone.product.ProductAdapter
import com.dicoding.projectcapstone.product.ProductModel
import com.dicoding.projectcapstone.product.ProductRepository
import com.dicoding.projectcapstone.product.ProductViewModelFactory
import com.dicoding.projectcapstone.ui.kategori.KategoriMakananActivity
import com.dicoding.projectcapstone.ui.kategori.KategoriMinumanActivity
import com.dicoding.projectcapstone.ui.kategori.LokasiActivity
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

    private val userModel: UserModel by viewModels {
        UserModelFactory(userRepository)
    }

    private val productViewModel: ProductModel by viewModels {
        ProductViewModelFactory(ProductRepository(apiService))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userRepository = UserRepository.getInstance(apiService)
        sessionManager = SessionManager(this)
        userModel.setSessionManager(sessionManager)

        if (!sessionManager.getIsLogin()) {
            navigateToLogin()
        } else {
            val isHorizontal = true
            setupRecyclerView(isHorizontal)
            setupAction()
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.home

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

        binding.btnFood.setOnClickListener {
            navigateWithLoading(KategoriMakananActivity::class.java)
        }

        binding.btnDrink.setOnClickListener {
            navigateWithLoading(KategoriMinumanActivity::class.java)
        }
    }

    private fun setupAction() {
        binding.txtName.text = sessionManager.getUsername()

        binding.button.setOnClickListener {
            userModel.logout()
            navigateToLogin()
        }

        productViewModel.fetchAllProducts()
    }

    private fun navigateToLogin() {
        navigateWithLoading(LoginActivity::class.java)
    }

    private fun setupRecyclerView(isHorizontal: Boolean) {
        val productAdapter = ProductAdapter(
            events = listOf(),
            onItemClick = { dataItem ->
                Log.d(
                    "MainActivity",
                    "Clicked item: ${dataItem.image?.let { helper.removePath(it) }}"
                )
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

        productViewModel.products.observe(this) { productList ->
            productList?.let {
                productAdapter.updateData(it)
            } ?: run {
                Log.d("MainActivity", "Product list is null or empty")
            }
        }
    }

    private fun navigateWithLoading(targetActivity: Class<*>) {
        val targetIntent = Intent(this, targetActivity)
        val loadingIntent = Intent(this, LoadingActivity::class.java).apply {
            putExtra("target_intent", targetIntent)
        }
        startActivity(loadingIntent)
    }
}
