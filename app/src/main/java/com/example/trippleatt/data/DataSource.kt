package com.example.trippleatt.data

import android.app.Activity

interface DataSource {

    suspend fun sendOtp(phoneNumber: String, activity: Activity) :Result<Boolean>

    suspend fun signInWithEmail(email: String, password: String) :Result<Boolean>

}