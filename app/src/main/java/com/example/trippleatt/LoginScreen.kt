package com.example.trippleatt

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
import java.util.concurrent.TimeUnit
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast

import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.google.android.gms.tasks.TaskExecutors

import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.auth.PhoneAuthCredential

class LoginScreen : AppCompatActivity() {

    private lateinit var btnGenerateOtp: Button
    private lateinit var etPhoneNumber: EditText
    private lateinit var goToBusinessAccount: TextView

    private var phoneNumber: String = ""
    private var otp: Int = 0

    private lateinit var auth: FirebaseAuth

    private lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private var verificationCode: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViews()

        Log.d(TAG, "Login Screen activity created")

        btnGenerateOtp.setOnClickListener {
            phoneNumber = etPhoneNumber.text.toString().trim()

            phoneNumber = "+91$phoneNumber"
            Log.d(TAG, phoneNumber)

            startPhoneNumberVerification(phoneNumber)

        }

        goToBusinessAccount.setOnClickListener {
            startActivity(Intent(this, BusinessLoginScreen::class.java))
        }

        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onCodeSent(verificationId: String, token: ForceResendingToken) {
                super.onCodeSent(verificationId,token)

                //Log.d(TAG, "onCodeSent:$verificationId")

                // Save verification ID and resending token so we can use them later
                verificationCode = verificationId
                Log.d(TAG, "Code sent Successfully.")
            }

            override fun onVerificationCompleted(
                credential: PhoneAuthCredential
            ) {
                val code = credential.smsCode
                if (code!=null){
                    verifyCode(code)
                }
                Log.d(TAG, "verification completed:$credential")
                Toast.makeText(this@LoginScreen, "verification completed", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.d(TAG, "onVerificationFailed:$e")
                Toast.makeText(this@LoginScreen, "onVerificationFailed:$e", Toast.LENGTH_LONG).show()

                if (e is FirebaseAuthInvalidCredentialsException){
                    Log.d(TAG, "FirebaseAuthInvalidCredentialsException: Invalid request")
                }
                else if(e is FirebaseTooManyRequestsException){
                    Log.d(TAG, "FirebaseTooManyRequestsException: The SMS quota for the project has been exceeded")
                }
            }

        }

    }

    fun startPhoneNumberVerification(phoneNumber: String){
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(mCallbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyCode(code: String){

        val intent = Intent(this, OtpVerification::class.java)
        intent.putExtra("code", code)
        intent.putExtra("verificationCode", verificationCode)
        startActivity(intent)
        //below line is used for getting getting credentials from our verification id and code.
        val credential = PhoneAuthProvider.getCredential(verificationCode, code)
        //after getting credential we are calling sign in method.
        //after getting credential we are calling sign in method.
        //signInWithCredential(credential)
    }

    fun findViews(){
        btnGenerateOtp = findViewById(R.id.btnSendOtp)

        etPhoneNumber = findViewById(R.id.etMobileNumber)

        goToBusinessAccount = findViewById(R.id.goToBusinessAccount)

        auth = Firebase.auth
    }



    companion object {
        private const val TAG = "PhoneAuthActivity"
    }
}