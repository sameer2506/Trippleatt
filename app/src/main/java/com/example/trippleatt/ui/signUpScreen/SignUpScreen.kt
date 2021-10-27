package com.example.trippleatt.ui.signUpScreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.trippleatt.databinding.ActivitySignUpScreenBinding
import com.example.trippleatt.security.isEmpty

class SignUpScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSetUpLocation.setOnClickListener {
            signUp()
        }
    }

    private fun signUp(){
        if (!isEmpty(binding.etFirstName)){
            binding.etFirstName.error = "It can't be empty."
            return
        }

        if (!isEmpty(binding.etLastName)){
            binding.etFirstName.error = "It can't be empty."
            return
        }

        val fName = binding.etFirstName.text.toString()
        val lName = binding.etLastName.text.toString()

        val intent = Intent(this, SignUpScreen2::class.java)
        intent.putExtra("fName", fName)
        intent.putExtra("lName", lName)
        startActivity(intent)
        finish()
    }
}