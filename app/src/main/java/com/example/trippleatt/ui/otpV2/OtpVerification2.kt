package com.example.trippleatt.ui.otpV2

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.trippleatt.AppPreferences
import com.example.trippleatt.data.Results
import com.example.trippleatt.databinding.ActivityOtpVerification2Binding
import com.example.trippleatt.ui.businessDetails.BusinessDetails
import com.example.trippleatt.ui.otpV.OtpVVM
import com.example.trippleatt.ui.otpV.OtpVVMF
import com.example.trippleatt.util.log
import com.example.trippleatt.util.toast
import com.google.firebase.auth.PhoneAuthProvider
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class OtpVerification2 : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private val factory: OtpVVMF by instance()

    private lateinit var binding: ActivityOtpVerification2Binding
    private lateinit var viewModel: OtpVVM
    private lateinit var view: View

    private lateinit var appPreferences: AppPreferences

    private lateinit var code: String
    private lateinit var verificationCode: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOtpVerification2Binding.inflate(layoutInflater)
        view = binding.root
        viewModel = ViewModelProvider(this, factory).get(OtpVVM::class.java)
        setContentView(view)

        appPreferences = AppPreferences(this)

        sendOtp(appPreferences.getPhoneNumber()!!)

        binding.btnOtpVerify.setOnClickListener {
            verifyOtp(verificationCode, code)
        }

        binding.textView4.setOnClickListener {
            sendOtp(appPreferences.getPhoneNumber()!!)
        }
    }

    private fun verifyOtp(verificationCode: String, code: String) {

        appPreferences.saveVerificationCode("")
        appPreferences.saveCode("")

        //below line is used for getting getting credentials from our verification id and code.
        val credential = PhoneAuthProvider.getCredential(verificationCode, code)

        viewModel.verifyOtp(credential)

        viewModel.verifyOtp.observe(this, {
            when (it) {
                is Results.Success -> {
                    val intent = Intent(this, BusinessDetails::class.java)
                    startActivity(intent)
                    finish()
                }
                is Results.Error -> {
                    log("signInWithEmail failure: ${it.exception.localizedMessage}")
                    toast("Authentication failed.")
                }
                is Results.Loading -> {
                }
            }
        })

    }

    private fun sendOtp(phoneNumber: String){
        viewModel.sendOtp(phoneNumber, this)

        code = appPreferences.getCode().toString()
        verificationCode = appPreferences.getVerificationCode().toString()

        binding.otpEditBox1.setText(code[0].toString())
        binding.otpEditBox2.setText(code[1].toString())
        binding.otpEditBox3.setText(code[2].toString())
        binding.otpEditBox4.setText(code[3].toString())
        binding.otpEditBox5.setText(code[4].toString())
        binding.otpEditBox6.setText(code[5].toString())
    }

}