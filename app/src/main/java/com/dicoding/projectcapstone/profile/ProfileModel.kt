package com.dicoding.projectcapstone.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.projectcapstone.profile.address.GetAddressResponse
import com.dicoding.projectcapstone.profile.address.NewAddressData
import com.dicoding.projectcapstone.utils.SessionManager
import kotlinx.coroutines.launch

class ProfileModel(private val repository: ProfileRepository) : ViewModel() {

    val addAddressResponse: LiveData<List<NewAddressData>> = repository.addAddressResponse


    private lateinit var sessionManager: SessionManager

    fun setSessionManager(sessionManager: SessionManager) {
        this.sessionManager = sessionManager
    }

}