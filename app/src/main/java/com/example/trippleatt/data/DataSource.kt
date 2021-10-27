package com.example.trippleatt.data

import android.app.Activity
import android.net.Uri

interface DataSource {

    suspend fun sendOtp(phoneNumber: String, activity: Activity) :Results<Boolean>

    suspend fun signInWithEmail(email: String, password: String) :Results<Boolean>

    suspend fun createUserAccount(email: String, password: String) : Results<Boolean>

    suspend fun uploadImage(data: Uri, rnds: Int) : Results<FileLink>

    suspend fun saveBusinessDetails(
        data: HashMap<String, Any>) : Results<Boolean>

    suspend fun getShopList() : Results<List<ShopListData>>

}