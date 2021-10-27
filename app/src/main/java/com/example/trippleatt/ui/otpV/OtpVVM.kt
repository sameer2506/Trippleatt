package com.example.trippleatt.ui.otpV

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trippleatt.data.Repository
import com.example.trippleatt.data.Results
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.coroutines.launch

class OtpVVM(
    private var repository: Repository
) : ViewModel() {

    private val _verifyOtp : MutableLiveData<Results<Boolean>> = MutableLiveData()
    val verifyOtp : LiveData<Results<Boolean>>
        get() = _verifyOtp

    fun verifyOtp(credential: PhoneAuthCredential) = viewModelScope.launch {
        _verifyOtp.value = Results.Loading
        _verifyOtp.value = repository.verifyOtp(credential)
    }

    private val _sendOtp : MutableLiveData<Results<Boolean>> = MutableLiveData()
    val sendOtp : LiveData<Results<Boolean>>
        get() = _sendOtp

    fun sendOtp(phoneNumber: String, activity: Activity) = viewModelScope.launch {
        _sendOtp.value = Results.Loading
        _sendOtp.value = repository.sendOtp(phoneNumber, activity)
    }
    
}