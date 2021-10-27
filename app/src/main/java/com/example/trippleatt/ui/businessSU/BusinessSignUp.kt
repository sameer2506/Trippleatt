package com.example.trippleatt.ui.businessSU

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.trippleatt.ui.bSU3.BusinessSignUp3
import com.example.trippleatt.ui.otpV.OtpVerification
import com.example.trippleatt.databinding.ActivityBusineesSignUpBinding
import com.example.trippleatt.security.isValidMail
import com.example.trippleatt.security.isValidMobile
import com.example.trippleatt.ui.businessLS.BusinessLoginScreen
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class BusinessSignUp : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private val factory: BusinessSUVMF by instance()

    private lateinit var binding: ActivityBusineesSignUpBinding
    private lateinit var viewModel: BusinessSUVM
    private lateinit var view: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBusineesSignUpBinding.inflate(layoutInflater)
        view = binding.root
        viewModel = ViewModelProvider(this, factory).get(BusinessSUVM::class.java)
        setContentView(view)

        binding.btnSendOtp.setOnClickListener {
            val data = binding.etUserDetails.text.toString().trim()

            if (isValidMail(data)) {
                val intent = Intent(this, BusinessSignUp3::class.java)
                intent.putExtra("email", data)
                startActivity(intent)
                finish()
            } else if (isValidMobile(data)) {
                var phoneNumber = binding.etUserDetails.text.toString().trim()
                phoneNumber = "+91$phoneNumber"
                startPhoneNumberVerification(phoneNumber)
            }
        }

        binding.goToBusinessSignIn.setOnClickListener {
            startActivity(Intent(this, BusinessLoginScreen::class.java))
            finish()
        }

    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        viewModel.sendOtp(phoneNumber, this)
        startActivity(Intent(this, OtpVerification::class.java))
        finish()
    }

}