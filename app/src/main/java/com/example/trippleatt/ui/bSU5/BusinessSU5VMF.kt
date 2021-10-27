package com.example.trippleatt.ui.bSU5

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trippleatt.data.Repository

@Suppress("UNCHECKED_CAST")
class BusinessSU5VMF(
    private val repository: Repository
    ) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BusinessSU5VM(repository) as T
    }
}