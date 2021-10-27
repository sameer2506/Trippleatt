package com.example.trippleatt.ui.otpV

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.trippleatt.AppPreferences
import com.example.trippleatt.R
import com.example.trippleatt.WelcomeScreen
import com.example.trippleatt.util.log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class OtpVerification : AppCompatActivity() {

    private lateinit var code: String
    private lateinit var verificationCode: String

    private lateinit var etOtpBox1: EditText
    private lateinit var etOtpBox2: EditText
    private lateinit var etOtpBox3: EditText
    private lateinit var etOtpBox4: EditText
    private lateinit var etOtpBox5: EditText
    private lateinit var etOtpBox6: EditText
    private lateinit var btnOtpVerify: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verification)

        initViews()

        val intent = intent

        auth = Firebase.auth

        code = intent.getStringExtra("code").toString()
        verificationCode = intent.getStringExtra("verificationCode").toString()

        val appPreferences = AppPreferences(this)

       // code = appPreferences.getCode().toString()
        //verificationCode = appPreferences.getVerificationCode().toString()

        code = appPreferences.getCode().toString()
        verificationCode = appPreferences.getVerificationCode().toString()

        log("testing")
        log("code: $code")

        etOtpBox1.setText(code[0].toString())
        etOtpBox2.setText(code[1].toString())
        etOtpBox3.setText(code[2].toString())
        etOtpBox4.setText(code[3].toString())
        etOtpBox5.setText(code[4].toString())
        etOtpBox6.setText(code[5].toString())

        btnOtpVerify.setOnClickListener {
            verifyOtp(verificationCode, code)
        }


    }

    fun verifyOtp(verificationCode: String, code: String){

        //below line is used for getting getting credentials from our verification id and code.
        val credential = PhoneAuthProvider.getCredential(verificationCode, code)

        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val intent  = Intent(this, WelcomeScreen::class.java)
                    startActivity(intent)
                } else{
                    Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun initViews(){
        etOtpBox1 = findViewById(R.id.otp_edit_box1)
        etOtpBox2 = findViewById(R.id.otp_edit_box2)
        etOtpBox3 = findViewById(R.id.otp_edit_box3)
        etOtpBox4 = findViewById(R.id.otp_edit_box4)
        etOtpBox5 = findViewById(R.id.otp_edit_box5)
        etOtpBox6 = findViewById(R.id.otp_edit_box6)
        btnOtpVerify = findViewById(R.id.btnOtpVerify)
    }

    companion object {
        private const val TAG = "PhoneAuthActivity"
    }
}