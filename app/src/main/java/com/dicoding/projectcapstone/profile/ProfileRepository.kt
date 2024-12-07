package com.dicoding.projectcapstone.profile

import GetAddressResponse
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.projectcapstone.API.ApiService
import com.dicoding.projectcapstone.profile.address.AddAdressRequest
import com.dicoding.projectcapstone.profile.address.Data
import retrofit2.HttpException

class ProfileRepository(private val apiService: ApiService) {

    private val _addAddressResponse = MutableLiveData<List<Data>>()
    val addAddressResponse: LiveData<List<Data>> get() = _addAddressResponse

    private val _getAddressResponse = MutableLiveData<List<GetAddressResponse>>()
    val getAddressResponse: LiveData<List<GetAddressResponse>> get() = _getAddressResponse

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    suspend fun addAddress(addressName: String) {
        try {
            Log.d("ProfileRepository", "Requesting to add address: $addressName")
            val request = AddAdressRequest(addressName)
            val response = apiService.addAddress(request)
            val dataList = listOf(response.data)
            _addAddressResponse.postValue(dataList as List<Data>?)
        } catch (e: HttpException) {
            Log.e("ProfileRepository", "HTTP error: ${e.code()} - ${e.message()}")
            _errorMessage.postValue("Failed to add address: HTTP ${e.code()} - ${e.message()}")
        } catch (e: Exception) {
            Log.e("ProfileRepository", "Unexpected error: ${e.message}")
            _errorMessage.postValue("Unexpected error1: ${e.message}")
        }
    }

    suspend fun fetchAddress(): GetAddressResponse? {
        return try {
            apiService.getAddress()
        } catch (e: Exception) {
            Log.e("ProfileRepository", "Error fetching address: ${e.message}")
            null
        }
    }


}
