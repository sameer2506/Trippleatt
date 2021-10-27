package com.example.trippleatt.ui.businessDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trippleatt.data.Repository

@Suppress("UNCHECKED_CAST")
class BusinessDetailsVMF(
    private val repository: Repository
    ) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BusinessDetailsVM(repository) as T
    }
}