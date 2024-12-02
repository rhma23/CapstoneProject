package com.dicoding.projectcapstone.product


import android.util.Log
import com.dicoding.projectcapstone.ApiService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ProductRepository(private val apiService: ApiService) {

    private val _products = MutableLiveData<List<DataItem>>()
    val products: LiveData<List<DataItem>> = _products

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    suspend fun getAllData() {
        try {
            val response = apiService.getAllProducts()
            Log.d("ProductRepository", "getAllData on Response: $response")
            _products.postValue(response.data as List<DataItem>?)
        } catch (e: Exception) {
            _errorMessage.postValue("Error: ${e.message}")
            Log.e("ProductRepository", "getAllData on Exception: ${e.message}")
        }
    }
}


