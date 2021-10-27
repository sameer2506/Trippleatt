package com.example.trippleatt.ui.bSU3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trippleatt.data.Repository
import com.example.trippleatt.data.Results
import kotlinx.coroutines.launch

class BusinessSU3VM(
    private val repository: Repository
    ) : ViewModel() {

    private val _createUserAccount : MutableLiveData<Results<Boolean>> = MutableLiveData()
    val createUserAccount : LiveData<Results<Boolean>>
        get() = _createUserAccount

    fun createUserAccount(email: String, password: String) = viewModelScope.launch {
        _createUserAccount.value = Results.Loading
        _createUserAccount.value = repository.createUserAccount(email, password)
    }

}