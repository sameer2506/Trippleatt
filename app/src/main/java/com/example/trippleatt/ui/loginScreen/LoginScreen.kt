package com.example.trippleatt.ui.loginScreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.trippleatt.AppPreferences
import com.example.trippleatt.ui.otpV.OtpVerification
import com.example.trippleatt.databinding.ActivityLoginBinding
import com.example.trippleatt.security.isValidMobile
import com.example.trippleatt.ui.businessLS.BusinessLoginScreen
import com.google.firebase.auth.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class LoginScreen : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private val factory: LoginScreenVMF by instance()

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginScreenVM
    private lateinit var view: View

    private var phoneNumber: String = ""

    private lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        view = binding.root
        viewModel = ViewModelProvider(this, factory).get(LoginScreenVM::class.java)
        setContentView(view)

        appPreferences = AppPreferences(this)

        binding.btnSendOtp.setOnClickListener {
            phoneNumber = binding.etMobileNumber.text.toString().trim()

            if (!isValidMobile(phoneNumber)){
                binding.etMobileNumber.error = "Invalid number"
                return@setOnClickListener
            }

            phoneNumber = "+91$phoneNumber"

            startPhoneNumberVerification(phoneNumber)
        }

        binding.goToBusinessAccount.setOnClickListener {
            startActivity(Intent(this, BusinessLoginScreen::class.java))
            finish()
        }

    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        viewModel.sendOtp(phoneNumber, this)
        appPreferences.savePhoneNumber(phoneNumber)
        startActivity(Intent(this, OtpVerification::class.java))
        finish()
    }

}