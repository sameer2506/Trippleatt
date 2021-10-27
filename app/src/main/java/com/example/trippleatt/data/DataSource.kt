package com.example.trippleatt.data

import android.app.Activity
import android.net.Uri

interface DataSource {

    suspend fun sendOtp(phoneNumber: String, activity: Activity) :Result<Boolean>

    suspend fun signInWithEmail(email: String, password: String) :Result<Boolean>

    suspend fun createUserAccount(email: String, password: String) : Result<Boolean>

    suspend fun uploadImage(data: Uri, rnds: Int) : Result<FileLink>

    suspend fun saveBusinessDetails(
        data: HashMap<String, Any>) : Result<Boolean>

}