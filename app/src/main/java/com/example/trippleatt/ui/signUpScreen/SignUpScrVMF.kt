package com.example.trippleatt.ui.signUpScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trippleatt.data.Repository

@Suppress("UNCHECKED_CAST")
class SignUpScrVMF(
    private val repository: Repository
    ) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SignUpScrVM(repository) as T
    }
}