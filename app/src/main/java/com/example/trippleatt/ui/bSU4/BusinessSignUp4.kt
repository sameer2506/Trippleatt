package com.example.trippleatt.ui.bSU4

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.trippleatt.databinding.ActivityBusinessSignUp4Binding
import com.example.trippleatt.ui.bSU5.BusinessSignUp5


class BusinessSignUp4 : AppCompatActivity() {

    private lateinit var binding: ActivityBusinessSignUp4Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBusinessSignUp4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvShop.setOnClickListener {
            startActivity(Intent(this, BusinessSignUp5::class.java))
            finish()
        }
    }
}