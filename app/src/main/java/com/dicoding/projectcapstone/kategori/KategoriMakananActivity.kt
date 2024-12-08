package com.dicoding.projectcapstone.kategori

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.projectcapstone.api.RetrofitClient
import com.dicoding.projectcapstone.R
import com.dicoding.projectcapstone.product.ProductModel
import com.dicoding.projectcapstone.product.ProductRepository
import com.dicoding.projectcapstone.product.ProductViewModelFactory
import com.dicoding.projectcapstone.kategori.adapter.CategoryAdapter

class KategoriMakananActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ProductModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kategori_makanan)
        recyclerView = findViewById(R.id.recycler_view_kategori_food)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val apiService = RetrofitClient.apiService
        val repository = ProductRepository(apiService)
        viewModel = ViewModelProvider(this, ProductViewModelFactory(repository))[ProductModel::class.java]
        viewModel.fetchProductsByCategory("Makanan")
        viewModel.categoryProducts.observe(this) { products ->
            if (products != null) {
                val adapter = CategoryAdapter(products)
                recyclerView.adapter = adapter
            } else {
                Toast.makeText(this, "Gagal memuat data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
