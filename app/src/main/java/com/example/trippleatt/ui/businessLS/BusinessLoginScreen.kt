package com.example.trippleatt.ui.businessLS

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.trippleatt.AppPreferences
import com.example.trippleatt.ui.businessSU.BusinessSignUp
import com.example.trippleatt.ui.otpV.OtpVerification
import com.example.trippleatt.WelcomeScreen
import com.example.trippleatt.data.Results
import com.example.trippleatt.databinding.ActivityBusinessLoginScreenBinding
import com.example.trippleatt.security.isValidMail
import com.example.trippleatt.security.isValidMobile
import com.example.trippleatt.util.log
import com.example.trippleatt.util.toast
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class BusinessLoginScreen : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private val factory: BusinessLSVMF by instance()

    private lateinit var binding: ActivityBusinessLoginScreenBinding
    private lateinit var viewModel: BusinessLSVM
    private lateinit var view: View

    private var flag = false

    private lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBusinessLoginScreenBinding.inflate(layoutInflater)
        view = binding.root
        viewModel = ViewModelProvider(this, factory).get(BusinessLSVM::class.java)
        setContentView(view)

        appPreferences = AppPreferences(this)

        binding.btnLogin.setOnClickListener {
            val userDetails = binding.etUserDetails.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (isValidMail(userDetails)) {
                flag = true
                signInWithEmail(userDetails, password)
            }
            else
                flag = false
            if (isValidMobile(userDetails)) {
                flag = true
                loginUsingMobile(userDetails)
            }
            else
                flag = false
            if (!flag){
                binding.etUserDetails.error = "Invalid format."
            }
        }

        binding.goToBusinessSignUp.setOnClickListener {
            startActivity(Intent(this, BusinessSignUp::class.java))
            finish()
        }

    }

    private fun loginUsingMobile(phone: String) {
        viewModel.sendOtp("+91$phone", this)
        appPreferences.savePhoneNumber(phone)
        startActivity(Intent(this, OtpVerification::class.java))
        finish()
    }

    private fun signInWithEmail(email: String, password: String) {

        viewModel.signInWithEmail(email, password)

        viewModel.signInWithEmail.observe(this, { result ->
            when (result) {
                is Results.Success -> {
                    val intent = Intent(this, WelcomeScreen::class.java)
                    startActivity(intent)
                }
                is Results.Error -> {
                    log("signInWithEmail failure: ${result.exception.localizedMessage}")
                    toast("Authentication failed.")
                }
                is Results.Loading -> {
                }
            }
        })
    }

}