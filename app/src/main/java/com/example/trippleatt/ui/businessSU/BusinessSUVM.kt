package com.example.trippleatt.ui.businessSU

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trippleatt.data.Repository
import com.example.trippleatt.data.Results
import kotlinx.coroutines.launch

class BusinessSUVM(
    private val repository: Repository
    ) : ViewModel() {

    private val _sendOtp : MutableLiveData<Results<Boolean>> = MutableLiveData()
    val sendOtp : LiveData<Results<Boolean>>
        get() = _sendOtp

    fun sendOtp(phoneNumber: String, activity: Activity) = viewModelScope.launch {
        _sendOtp.value = Results.Loading
        _sendOtp.value = repository.sendOtp(phoneNumber, activity)
    }

}