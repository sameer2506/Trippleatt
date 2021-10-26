package com.example.trippleatt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.trippleatt.ui.BusinessLS.BusinessLoginScreen
import com.example.trippleatt.ui.LoginScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this, LoginScreen::class.java))
    }
}