package com.example.trippleatt.data

import android.app.Activity
import android.net.Uri
import com.google.firebase.auth.PhoneAuthCredential

interface DataSource {

    suspend fun sendOtp(phoneNumber: String, activity: Activity) :Results<Boolean>

    suspend fun signInWithEmail(email: String, password: String) :Results<Boolean>

    suspend fun createUserAccount(email: String, password: String) : Results<Boolean>

    suspend fun uploadImage(data: Uri, rnds: Int) : Results<FileLink>

    suspend fun saveBusinessDetails(
        data: HashMap<String, Any>) : Results<Boolean>

    suspend fun getShopList() : Results<List<ShopListData>>

    suspend fun verifyOtp(credential: PhoneAuthCredential) : Results<Boolean>

    suspend fun saveUserDetails(data: HashMap<String, Any>) : Results<Boolean>

}