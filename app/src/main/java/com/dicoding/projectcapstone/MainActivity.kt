package com.dicoding.projectcapstone

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.projectcapstone.api.RetrofitClient
import com.dicoding.projectcapstone.api.RetrofitClient.apiService
import com.dicoding.projectcapstone.banner.BannerFactory
import com.dicoding.projectcapstone.banner.BannerModel
import com.dicoding.projectcapstone.banner.BannerRepository
import com.dicoding.projectcapstone.banner.Weather
import com.dicoding.projectcapstone.banner.weather.WeatherModel
import com.dicoding.projectcapstone.banner.weather.WeatherModelFactory
import com.dicoding.projectcapstone.banner.weather.WeatherRepository
import com.dicoding.projectcapstone.databinding.ActivityMainBinding
import com.dicoding.projectcapstone.kategori.KategoriMakananActivity
import com.dicoding.projectcapstone.kategori.KategoriMinumanActivity
import com.dicoding.projectcapstone.login.LoginActivity
import com.dicoding.projectcapstone.product.AllProductAdapter
import com.dicoding.projectcapstone.product.ProductRecomendationAdapter
import com.dicoding.projectcapstone.product.ProductModel
import com.dicoding.projectcapstone.product.ProductRepository
import com.dicoding.projectcapstone.product.ProductViewModelFactory
import com.dicoding.projectcapstone.profile.ProfileActivity
import com.dicoding.projectcapstone.profile.address.EditAddressFragment
import com.dicoding.projectcapstone.location.LokasiActivity
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

    private val weatherData = listOf(
        Weather(
            id = 1,
            category = "Rain",
            description = "Cuaca hujan bikin suasana lebih nyaman, ayo lengkapi harimu dengan yang terbaik! Jangan sampai terlewat.",
            imageUrl = "https://i.pinimg.com/736x/7a/44/19/7a44199aff3fd42c45b1807feb518fa4.jpg"
        ),
        Weather(
            id = 2,
            category = "Drizzle",
            description = "Langit mendung, tapi semangat tetap harus cerah! Buat harimu lebih seru dengan hal istimewa ini!",
            imageUrl = "https://i.pinimg.com/736x/28/85/71/28857188f7a6757d5dba9d3f339f1bec.jpg"
        ),
        Weather(
            id = 3,
            category = "Clouds",
            description = "Cuaca panas? Waktunya cari sesuatu yang bikin segar dan nyaman. Yuk, jangan tunggu lama-lama!",
            imageUrl = "https://i.pinimg.com/736x/b9/39/79/b939794266cf899fbbd55435efd2a402.jpg"
        ),
        Weather(
            id = 4,
            category = "Clear",
            description = "Matahari bersinar terang, saatnya menikmati hari dengan penuh semangat. Temukan pilihan yang bikin harimu lebih spesial!",
            imageUrl = "https://i.pinimg.com/736x/ff/08/a6/ff08a64470b936483bc51b823cb726eb.jpg"
        )
    )

    private val userModel: UserModel by viewModels {
        UserModelFactory(userRepository)
    }

    private val productViewModel: ProductModel by viewModels {
        ProductViewModelFactory(ProductRepository(apiService))
    }

    private val bannerModel: BannerModel by viewModels {
        BannerFactory(BannerRepository(apiService))
    }

    private val weatherModel: WeatherModel by viewModels {
        WeatherModelFactory(WeatherRepository(apiService))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize RetrofitClient with application context
        RetrofitClient.initialize(applicationContext)

        // Initialize the sessionManager and userRepository properties
        sessionManager = SessionManager(this)
        userRepository = UserRepository.getInstance(apiService)

        userModel.setSessionManager(sessionManager)
        weatherModel.fetchDataWeather()

        if (!sessionManager.getIsLogin()) {
            navigateToLogin()
        } else {
            setupHome() // Initialize the main home setup
            val address = sessionManager.getAddressUser()
            Log.d("MainActivity", "onCreate: $address")
            if (address.isNullOrEmpty()) {
                if (!isFinishing) {
                    AlertDialog.Builder(this).apply {
                        setTitle("Hallo!")
                        setMessage("Hello, this account has not added an address. Add address first.")
                        setPositiveButton("Add Address") { _, _ ->
                            Log.i("MainActivity", "onCreate: Add Address")
//                            navigateToFragment(EditAddressFragment())
                        }
                        create()
                        show()
                    }
                }
            }
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

    fun setupHome() {
        val isHorizontal = true
        productRecommendationAdapter(true)
        allProductAdapter(isHorizontal)
        setupViewBaner()
        setupAction()
    }


    private fun setupAction() {
        binding.txtName.text = sessionManager.getUsername()

//        binding.button.setOnClickListener {
//            userModel.logout()
//            navigateToLogin()
//        }

        productViewModel.fetchAllProducts()
        productViewModel.fetchAllProductsRecommendations()
    }

    private fun navigateToLogin() {
        navigateWithLoading(LoginActivity::class.java)
    }

    private fun productRecommendationAdapter(isHorizontal: Boolean) {
        val productAdapter = ProductRecomendationAdapter(
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

        productViewModel.productsRecommendations.observe(this) { productList ->
            productList?.let {
                productAdapter.updateData(it)
            } ?: run {
                Log.d("MainActivity", "Product list is null or empty")
            }
        }
    }

    private fun allProductAdapter(isHorizontal: Boolean) {
        val allProductAdapter = AllProductAdapter(
            events = listOf(),
            onItemClick = { dataItem ->
                Log.d(
                    "MainActivity",
                    "Clicked item: ${dataItem.image?.let { helper.removePath(it) }}"
                )
                Toast.makeText(this, "Clicked: ${dataItem.name}", Toast.LENGTH_SHORT).show()
            }
        )

        // Menggunakan StaggeredGridLayoutManager
        val layoutManager = StaggeredGridLayoutManager(
            2, // Jumlah kolom
            StaggeredGridLayoutManager.VERTICAL // Orientasi vertikal
        )

        binding.rvAllProduct.apply {
            this.layoutManager = layoutManager
            adapter = allProductAdapter
            setHasFixedSize(true)
        }

        productViewModel.products.observe(this) { productList ->
            productList?.let {
                allProductAdapter.updateData(it)
            } ?: run {
                Log.d("MainActivity", "Product list is null or empty")
            }
        }
    }

    private fun setupViewBaner() {
    val handler = Handler(Looper.getMainLooper())
    val runnable = object : Runnable {
        override fun run() {
            if (isDestroyed) return // Check if the activity is destroyed

            val weather_main = weatherModel.weather.value?.data?.weather_main.toString()
            binding.txtWeather.text = weather_main
            binding.txtTemperature.text = helper.roundToNearestInteger(weatherModel.weather.value?.data?.temperature.toString()).toString()
            Log.d("MainActivity", "Weather: $weather_main")
            weatherData.forEach {
                if (it.category == weather_main) {
                    Glide.with(this@MainActivity).load(it.imageUrl).into(binding.imgBackground)
                    binding.txtWeatherMessage.text = it.description
                }
            }
            handler.postDelayed(this, 1000)
        }
    }
    handler.post(runnable)
}

    private fun navigateWithLoading(targetActivity: Class<*>) {
        binding.loading.visibility = View.VISIBLE // Tampilkan ProgressBar
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, targetActivity))
            binding.loading.visibility = View.GONE // Sembunyikan ProgressBar
        }, 1500)
    }

    private fun navigateToFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}