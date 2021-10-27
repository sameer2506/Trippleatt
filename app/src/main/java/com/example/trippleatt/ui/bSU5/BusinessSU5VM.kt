package com.example.trippleatt.ui.bSU5

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trippleatt.data.Repository
import com.example.trippleatt.data.Results
import com.example.trippleatt.data.ShopListData
import kotlinx.coroutines.launch

class BusinessSU5VM(
    private val repository: Repository
    ) : ViewModel() {

    private val _getShopList : MutableLiveData<Results<List<ShopListData>>> = MutableLiveData()
    val getShopList : LiveData<Results<List<ShopListData>>>
        get() = _getShopList

    fun getShopList() = viewModelScope.launch {
        _getShopList.value = Results.Loading
        _getShopList.value = repository.getShopList()
    }

}