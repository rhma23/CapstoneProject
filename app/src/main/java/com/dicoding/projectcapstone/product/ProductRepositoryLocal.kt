package com.dicoding.projectcapstone.product


import android.content.Context
import com.dicoding.projectcapstone.API.ApiService
import com.dicoding.projectcapstone.product.data.AppDatabase
import com.dicoding.projectcapstone.product.data.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepositoryLocal(private val apiService: ApiService, context: Context) {
    private val productDao = AppDatabase.getDatabase(context).productDao()

    suspend fun getProducts(): List<Product> {
        return withContext(Dispatchers.IO) {
            val cachedProducts = productDao.getAllProducts()
            if (cachedProducts.isNotEmpty()) {
                cachedProducts
            } else {
                val apiProducts = apiService.getProducts()
                productDao.insertProducts(apiProducts)
                apiProducts
            }
        }
    }

    suspend fun updateProducts() {
        withContext(Dispatchers.IO) {
            val apiProducts = apiService.getProducts()
            productDao.deleteAllProducts()
            productDao.insertProducts(apiProducts)
        }
    }
}