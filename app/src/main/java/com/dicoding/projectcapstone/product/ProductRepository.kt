package com.dicoding.projectcapstone.product

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.projectcapstone.api.ApiService
import retrofit2.Response

class ProductRepository(private val apiService: ApiService) {

    private val _products = MutableLiveData<List<DataItem>>()
    val products: LiveData<List<DataItem>> = _products

    private val _productsRecommendations = MutableLiveData<List<DataItem>>()
    val productsRecommendations: LiveData<List<DataItem>> = _productsRecommendations

    private val _productDetail = MutableLiveData<ProductDetailResponse>()
    val productDetail: LiveData<ProductDetailResponse> = _productDetail

    private val _productRecomendationFromMl = MutableLiveData<List<DataItemResponse>>()
    val productRecomendationFromMl: LiveData<List<DataItemResponse>> = _productRecomendationFromMl

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    suspend fun getAllProducts() {
        try {
            val response = apiService.getAllProducts()
            Log.d("ProductRepository", "getAllData on Response: $response")
            _products.postValue(response.data as List<DataItem>?)
        } catch (e: Exception) {
            _errorMessage.postValue("Error: ${e.message}")
            Log.e("ProductRepository", "getAllData on Exception: ${e.message}")
        }
    }

    suspend fun getAllRecommendationProducts() {
        try {
            val response = apiService.getAllRecommendationProducts()
            Log.d("ProductRepository", "getAllData on Response: $response")
            _productsRecommendations.postValue(response.data as List<DataItem>?)
        } catch (e: Exception) {
            _errorMessage.postValue("Error: ${e.message}")
            Log.e("ProductRepository", "getAllData on Exception: ${e.message}")
        }
    }

    suspend fun getProductRecommendationFromMl() {
        try {
            val response = apiService.getProductsRecomendationByMl()
            Log.d("ProductRepository", "getAllDataRecomendaationByMl on Response: $response")
            _productRecomendationFromMl.postValue(response.data as List<DataItemResponse>?)
        } catch (e: Exception) {
            _errorMessage.postValue("Error: ${e.message}")
            Log.e("ProductRepository", "getAllDataRecomendaationByMl on Exception: ${e.message}")
        }
    }

    suspend fun getProductsByCategory(category: String): List<DataItem>? {
        return try {
            val response = apiService.getProductsByCategory(category)
            if (response.success == true) {
                response.data as List<DataItem>?
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("ProductRepository", "Error fetching category: ${e.message}")
            null
        }
    }

    suspend fun getProductById(id: Int): ProductDetailResponse? {
        return apiService.getProductById(id)
    }

}