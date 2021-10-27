package com.example.trippleatt

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(ctx: Context) {

    private var verificationCode: SharedPreferences = ctx.getSharedPreferences("APP_PREFRENCES",Context.MODE_PRIVATE)
    private var codeForVerify: SharedPreferences = ctx.getSharedPreferences("APP_PREFRENCES",Context.MODE_PRIVATE)
    private var phoneNumber: SharedPreferences = ctx.getSharedPreferences("APP_PREFRENCES",Context.MODE_PRIVATE)

    fun saveVerificationCode(verification: String){
        verificationCode.edit().putString("verificationCode",verification).apply()
    }

    fun getVerificationCode(): String? {
        return verificationCode.getString("verificationCode","")
    }

    fun saveCode(code: String){
        codeForVerify.edit().putString("code",code).apply()
    }

    fun getCode(): String? {
        return codeForVerify.getString("code","")
    }

    fun savePhoneNumber(phoneNumber: String){
        codeForVerify.edit().putString("phoneNumber",phoneNumber).apply()
    }

    fun getPhoneNumber(): String? {
        return codeForVerify.getString("phoneNumber","")
    }
}