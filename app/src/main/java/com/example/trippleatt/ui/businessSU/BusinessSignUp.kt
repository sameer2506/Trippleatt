package com.example.trippleatt.ui.businessSU

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.trippleatt.ui.bSU3.BusinessSignUp3
import com.example.trippleatt.OtpVerification
import com.example.trippleatt.databinding.ActivityBusineesSignUpBinding
import com.example.trippleatt.ui.businessLS.BusinessLoginScreen
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.util.regex.Pattern


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
    }

    private fun isValidMail(email: String): Boolean {
        val EMAIL_STRING = ("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        return Pattern.compile(EMAIL_STRING).matcher(email).matches()
    }

    private fun isValidMobile(phone: String): Boolean {
        return if (!Pattern.matches("^[+91][0-9]{10}$", phone)) {
            phone.length == 10
        } else false
    }

}