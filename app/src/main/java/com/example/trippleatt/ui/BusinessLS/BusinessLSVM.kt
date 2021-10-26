package com.example.trippleatt.ui.BusinessLS

import android.app.Activity
import androidx.lifecycle.*
import com.example.trippleatt.data.Repository
import com.example.trippleatt.data.Result
import com.example.trippleatt.data.repository.BusinessLSRepo
import kotlinx.coroutines.launch

class BusinessLSVM(
    private val repository: Repository
    ) : ViewModel() {

    private val _signInWithEmail : MutableLiveData<Result<Boolean>> = MutableLiveData()
    val signInWithEmail : LiveData<Result<Boolean>>
        get() = _signInWithEmail

    fun signInWithEmail(email: String, password: String) = viewModelScope.launch {
        _signInWithEmail.value = Result.Loading
        _signInWithEmail.value = repository.signInWithEmail(email, password)
    }

}