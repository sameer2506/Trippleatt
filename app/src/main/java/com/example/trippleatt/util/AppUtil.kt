package com.example.trippleatt.util

import android.content.Context
import android.util.Log
import android.widget.Toast

fun log(message: String){
    Log.d("Trippleatt_log_data",message)
}

fun Context.toast(message:String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
