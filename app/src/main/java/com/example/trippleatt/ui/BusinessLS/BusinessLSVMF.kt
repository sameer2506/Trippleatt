package com.example.trippleatt.ui.BusinessLS

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trippleatt.data.Repository
import com.example.trippleatt.data.repository.BusinessLSRepo

@Suppress("UNCHECKED_CAST")
class BusinessLSVMF(
    private val repository: Repository
    ) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BusinessLSVM(repository) as T
    }
}