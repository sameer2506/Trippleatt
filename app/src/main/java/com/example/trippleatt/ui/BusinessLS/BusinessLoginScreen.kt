package com.example.trippleatt.ui.BusinessLS

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.trippleatt.R
import com.example.trippleatt.WelcomeScreen
import com.example.trippleatt.data.Result
import com.example.trippleatt.databinding.ActivityBusinessLoginScreenBinding
import com.example.trippleatt.databinding.ActivityLoginBinding
import com.example.trippleatt.ui.DataViewModel
import com.example.trippleatt.ui.ViewModelFactory
import com.example.trippleatt.util.log
import com.example.trippleatt.util.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class BusinessLoginScreen : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private val factory: BusinessLSVMF by instance()

    private lateinit var binding: ActivityBusinessLoginScreenBinding
    private lateinit var viewModel: BusinessLSVM
    private lateinit var view: View

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBusinessLoginScreenBinding.inflate(layoutInflater)
        view = binding.root
        viewModel = ViewModelProvider(this, factory).get(BusinessLSVM::class.java)
        setContentView(view)

        auth = Firebase.auth

        binding.btnLogin.setOnClickListener {
            val userDetails = binding.etUserDetails.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (isValidMail(userDetails)) {
                signInWithEmail(userDetails, password)
            } else if (isValidMobile(userDetails)) {
                loginUsingMobile(userDetails)
            }
        }

    }

    private fun loginUsingMobile(phone: String) {
    }

    private fun signInWithEmail(email: String, password: String) {

        viewModel.signInWithEmail(email, password)

        viewModel.signInWithEmail.observe(this, { result ->
            when (result) {
                is Result.Success -> {
                    val intent = Intent(this, WelcomeScreen::class.java)
                    startActivity(intent)
                }
                is Result.Error -> {
                    log("signInWithEmail failure: ${result.exception.localizedMessage}")
                    toast("Authentication failed.")
                }
                is Result.Loading -> {
                }
            }
        })
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

}