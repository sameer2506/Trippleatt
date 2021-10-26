package com.example.trippleatt.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast

import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.trippleatt.ui.businessLS.BusinessLoginScreen
import com.example.trippleatt.OtpVerification
import com.example.trippleatt.R
import com.example.trippleatt.databinding.ActivityLoginBinding
import com.example.trippleatt.util.log

import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthCredential

import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class LoginScreen : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private val factory: ViewModelFactory by instance()

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: DataViewModel
    private lateinit var view: View

    private lateinit var btnGenerateOtp: Button
    private lateinit var etPhoneNumber: EditText
    private lateinit var goToBusinessAccount: TextView

    private var phoneNumber: String = ""

    private lateinit var auth: FirebaseAuth

    private lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private var verificationCode: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        view = binding.root
        viewModel = ViewModelProvider(this, factory).get(DataViewModel::class.java)
        setContentView(view)

        findViews()

        log("Login Screen activity created")

        binding.btnSendOtp.setOnClickListener {
            phoneNumber = binding.etMobileNumber.text.toString().trim()

            phoneNumber = "+91$phoneNumber"

            startPhoneNumberVerification(phoneNumber)
        }

        binding.goToBusinessAccount.setOnClickListener {
            startActivity(Intent(this, BusinessLoginScreen::class.java))
        }

        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onCodeSent(verificationId: String, token: ForceResendingToken) {
                super.onCodeSent(verificationId, token)

                //Log.d(TAG, "onCodeSent:$verificationId")

                // Save verification ID and resending token so we can use them later
                verificationCode = verificationId
                Log.d(TAG, "Code sent Successfully.")
            }

            override fun onVerificationCompleted(
                credential: PhoneAuthCredential
            ) {
                val code = credential.smsCode
                if (code != null) {
                    verifyCode(code)
                }
                Log.d(TAG, "verification completed:$credential")
                Toast.makeText(this@LoginScreen, "verification completed", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.d(TAG, "onVerificationFailed:$e")
                Toast.makeText(this@LoginScreen, "onVerificationFailed:$e", Toast.LENGTH_LONG)
                    .show()

                if (e is FirebaseAuthInvalidCredentialsException) {
                    Log.d(TAG, "FirebaseAuthInvalidCredentialsException: Invalid request")
                } else if (e is FirebaseTooManyRequestsException) {
                    Log.d(
                        TAG,
                        "FirebaseTooManyRequestsException: The SMS quota for the project has been exceeded"
                    )
                }
            }

        }

    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        viewModel.sendOtp(phoneNumber, this)

        startActivity(Intent(this, OtpVerification::class.java))

    }

    fun verifyCode(code: String) {

        val intent = Intent(this, OtpVerification::class.java)
        intent.putExtra("code", code)
        intent.putExtra("verificationCode", verificationCode)
        startActivity(intent)
    }

    fun findViews() {
        btnGenerateOtp = findViewById(R.id.btnSendOtp)

        etPhoneNumber = findViewById(R.id.etMobileNumber)

        goToBusinessAccount = findViewById(R.id.goToBusinessAccount)

        auth = Firebase.auth
    }


    companion object {
        private const val TAG = "PhoneAuthActivity"
    }
}