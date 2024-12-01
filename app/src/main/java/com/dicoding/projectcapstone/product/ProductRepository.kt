package com.dicoding.projectcapstone.product

import com.dicoding.projectcapstone.API.ApiService

class ProductRepository(private val apiService: ApiService)  {
    suspend fun getAllProduct() = apiService.getAllProducts()
    companion object {
        @Volatile
        private var instance: ProductRepository? = null
        fun getInstance(
            apiService: ApiService
        ): ProductRepository =
            instance ?: synchronized(this) {
                instance ?: ProductRepository(apiService)
            }.also { instance = it }
    }
}