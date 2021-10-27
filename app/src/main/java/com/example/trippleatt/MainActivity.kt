package com.example.trippleatt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.trippleatt.ui.bSU5.BusinessSignUp5
import com.example.trippleatt.ui.businessLS.BusinessLoginScreen
import com.example.trippleatt.ui.loginScreen.LoginScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this, BusinessLoginScreen::class.java))
    }
}