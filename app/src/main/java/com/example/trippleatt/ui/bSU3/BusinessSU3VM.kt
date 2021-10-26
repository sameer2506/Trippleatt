package com.example.trippleatt.ui.bSU3

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trippleatt.data.Repository
import com.example.trippleatt.data.Result
import kotlinx.coroutines.launch

class BusinessSU3VM(
    private val repository: Repository
    ) : ViewModel() {

    private val _createUserAccount : MutableLiveData<Result<Boolean>> = MutableLiveData()
    val createUserAccount : LiveData<Result<Boolean>>
        get() = _createUserAccount

    fun createUserAccount(email: String, password: String) = viewModelScope.launch {
        _createUserAccount.value = Result.Loading
        _createUserAccount.value = repository.createUserAccount(email, password)
    }

}