package com.example.trippleatt.ui.businessDetails

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trippleatt.data.FileLink
import com.example.trippleatt.data.Repository
import com.example.trippleatt.data.Result
import kotlinx.coroutines.launch

class BusinessDetailsVM(
    private var repository: Repository
) : ViewModel() {

    private val _uploadImage : MutableLiveData<Result<FileLink>> = MutableLiveData()
    val uploadImage : LiveData<Result<FileLink>>
        get() = _uploadImage

    fun uploadImage(data: Uri, rnds: Int) = viewModelScope.launch {
        _uploadImage.value = Result.Loading
        _uploadImage.value = repository.uploadImage(data, rnds)
    }

    private val _saveBusinessDetails : MutableLiveData<Result<Boolean>> = MutableLiveData()
    val saveBusinessDetails : LiveData<Result<Boolean>>
        get() = _saveBusinessDetails

    fun saveBusinessDetails(data: HashMap<String, Any>) = viewModelScope.launch {
        _saveBusinessDetails.value = Result.Loading
        _saveBusinessDetails.value = repository.saveBusinessDetails(data)
    }
    
}