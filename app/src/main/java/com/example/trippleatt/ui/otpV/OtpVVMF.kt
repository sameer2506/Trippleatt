package com.example.trippleatt.ui.otpV

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trippleatt.data.Repository

@Suppress("UNCHECKED_CAST")
class OtpVVMF(
    private val repository: Repository
    ) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OtpVVM(repository) as T
    }
}