package com.dicoding.projectcapstone.otp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OtpModelFactory(
    private val repository: OtpRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(OtpModel::class.java)) {
            OtpModel(repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}