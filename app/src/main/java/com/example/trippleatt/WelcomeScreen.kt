package com.example.trippleatt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.trippleatt.databinding.ActivityWelcomeScreenBinding
import com.example.trippleatt.ui.businessLS.BusinessLoginScreen
import com.example.trippleatt.ui.businessSU.BusinessSignUp
import com.example.trippleatt.ui.signUpScreen.SignUpScreen

class WelcomeScreen : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCustomerSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpScreen::class.java))
            finish()
        }

        binding.btnBusinessSignUp.setOnClickListener {
            startActivity(Intent(this, BusinessSignUp::class.java))
            finish()
        }

        binding.btnExistShop.setOnClickListener {
            finish()
        }

        binding.btnBusinessSignIn.setOnClickListener {
            startActivity(Intent(this, BusinessLoginScreen::class.java))
            finish()
        }
    }
}