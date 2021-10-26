package com.example.trippleatt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.trippleatt.ui.LoginScreen
import com.example.trippleatt.ui.bSU5.BusinessSignUp5

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this, BusinessSignUp5::class.java))
    }
}