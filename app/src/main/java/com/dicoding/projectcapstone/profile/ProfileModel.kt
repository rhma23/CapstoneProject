package com.dicoding.projectcapstone.profile

import GetAddressResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.projectcapstone.profile.address.Data
import com.dicoding.projectcapstone.utils.SessionManager
import kotlinx.coroutines.launch

class ProfileModel(private val repository: ProfileRepository) : ViewModel() {

    val addAddressResponse: LiveData<List<Data>> = repository.addAddressResponse

    private val _getAddressResponse = MutableLiveData<GetAddressResponse?>()
    val getAddressResponse: LiveData<GetAddressResponse?> = _getAddressResponse

    val errorMessage: LiveData<String> = repository.errorMessage

    private lateinit var sessionManager: SessionManager

    fun setSessionManager(sessionManager: SessionManager) {
        this.sessionManager = sessionManager
    }

    fun addAddress(addressName: String) {
        viewModelScope.launch {
            repository.addAddress(addressName)
        }
    }

    fun fetchAddress() {
        viewModelScope.launch {
            _getAddressResponse.value = repository.fetchAddress()
        }
    }
}