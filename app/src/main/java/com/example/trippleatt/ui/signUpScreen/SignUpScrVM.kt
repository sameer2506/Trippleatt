package com.example.trippleatt.ui.signUpScreen

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trippleatt.data.Repository
import com.example.trippleatt.data.Results
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.coroutines.launch

class SignUpScrVM(
    private var repository: Repository
) : ViewModel() {

    private val _saveUserDetails: MutableLiveData<Results<Boolean>> = MutableLiveData()
    val saveUserDetails: LiveData<Results<Boolean>>
        get() = _saveUserDetails

    fun saveUserDetails(data: HashMap<String, Any>) = viewModelScope.launch {
        _saveUserDetails.value = Results.Loading
        _saveUserDetails.value = repository.saveUserDetails(data)
    }

}