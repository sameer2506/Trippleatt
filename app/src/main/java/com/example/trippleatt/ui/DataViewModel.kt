package com.example.trippleatt.ui

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trippleatt.data.OtpSend
import com.example.trippleatt.data.Repository
import com.example.trippleatt.data.Result
import kotlinx.coroutines.launch

class DataViewModel(
    private var repository: Repository
) : ViewModel() {

    private val _sendOtp : MutableLiveData<Result<Boolean>> = MutableLiveData()
    val sendOtp : LiveData<Result<Boolean>>
        get() = _sendOtp

    fun sendOtp(phoneNumber: String, activity: Activity) = viewModelScope.launch {
        _sendOtp.value = Result.Loading
        _sendOtp.value = repository.sendOtp(phoneNumber, activity)
    }
    
}