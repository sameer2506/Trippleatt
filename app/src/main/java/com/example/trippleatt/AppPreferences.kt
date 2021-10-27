package com.example.trippleatt

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(ctx: Context) {

    private var verificationCode: SharedPreferences = ctx.getSharedPreferences("APP_PREFRENCES",Context.MODE_PRIVATE)
    private var codeForVerify: SharedPreferences = ctx.getSharedPreferences("APP_PREFRENCES",Context.MODE_PRIVATE)
    private var phoneNumber: SharedPreferences = ctx.getSharedPreferences("APP_PREFRENCES",Context.MODE_PRIVATE)
    private var shopId: SharedPreferences = ctx.getSharedPreferences("APP_PREFRENCES",Context.MODE_PRIVATE)

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

    fun savePhoneNumber(phone: String){
        phoneNumber.edit().putString("phoneNumber",phone).apply()
    }

    fun getPhoneNumber(): String? {
        return phoneNumber.getString("phoneNumber","")
    }

    fun saveShopId(id: Long){
        shopId.edit().putLong("shopId", id).apply()
    }

    fun getShopId(): Long {
        return shopId.getLong("shopId", 0L)
    }
}