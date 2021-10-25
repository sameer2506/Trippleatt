package com.example.trippleatt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class BusinessLoginScreen : AppCompatActivity() {

    private lateinit var etUserDetails: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_login_screen)

        initView()

        auth = Firebase.auth

        btnLogin.setOnClickListener {
            val userDetails = etUserDetails.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (isValidMail(userDetails)){
                signInWithEmail(userDetails, password)
            }
            else if (isValidMobile(userDetails)){
                loginUsingMobile(userDetails)
            }
        }

    }

    fun loginUsingMobile(phone: String){
    }

    fun signInWithEmail(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val intent  = Intent(this, WelcomeScreen::class.java)
                    startActivity(intent)
                } else{
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun isValidMail(email: String): Boolean {
        val EMAIL_STRING = ("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        return Pattern.compile(EMAIL_STRING).matcher(email).matches()
    }

    private fun isValidMobile(phone: String): Boolean {
        return if (!Pattern.matches("[a-zA-Z]+", phone)) {
            phone.length == 10
        } else false
    }

    fun initView(){
        etUserDetails = findViewById(R.id.etUserDetails)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
    }

    companion object {
        private const val TAG = "PhoneAuthActivity"
    }
}