package com.example.trippleatt.ui.loginScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trippleatt.data.Repository

@Suppress("UNCHECKED_CAST")
class LoginScreenVMF(
    private val repository: Repository
    ) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginScreenVM(repository) as T
    }
}