package com.example.trippleatt.ui.businessLS

import android.app.Activity
import androidx.lifecycle.*
import com.example.trippleatt.data.Repository
import com.example.trippleatt.data.Result
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

    private val _sendOtp : MutableLiveData<Result<Boolean>> = MutableLiveData()
    val sendOtp : LiveData<Result<Boolean>>
        get() = _sendOtp

    fun sendOtp(phoneNumber: String, activity: Activity) = viewModelScope.launch {
        _sendOtp.value = Result.Loading
        _sendOtp.value = repository.sendOtp(phoneNumber, activity)
    }

}