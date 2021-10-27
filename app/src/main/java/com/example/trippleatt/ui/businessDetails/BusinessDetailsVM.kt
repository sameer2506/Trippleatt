package com.example.trippleatt.ui.businessDetails

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trippleatt.data.FileLink
import com.example.trippleatt.data.Repository
import com.example.trippleatt.data.Results
import kotlinx.coroutines.launch

class BusinessDetailsVM(
    private var repository: Repository
) : ViewModel() {

    private val _uploadImage : MutableLiveData<Results<FileLink>> = MutableLiveData()
    val uploadImage : LiveData<Results<FileLink>>
        get() = _uploadImage

    fun uploadImage(data: Uri, rnds: Int) = viewModelScope.launch {
        _uploadImage.value = Results.Loading
        _uploadImage.value = repository.uploadImage(data, rnds)
    }

    private val _saveBusinessDetails : MutableLiveData<Results<Boolean>> = MutableLiveData()
    val saveBusinessDetails : LiveData<Results<Boolean>>
        get() = _saveBusinessDetails

    fun saveBusinessDetails(data: HashMap<String, Any>) = viewModelScope.launch {
        _saveBusinessDetails.value = Results.Loading
        _saveBusinessDetails.value = repository.saveBusinessDetails(data)
    }
    
}