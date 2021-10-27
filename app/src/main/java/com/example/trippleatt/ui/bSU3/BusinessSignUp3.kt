package com.example.trippleatt.ui.bSU3

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.trippleatt.data.Results
import com.example.trippleatt.databinding.ActivityBusinessSignUp3Binding
import com.example.trippleatt.ui.bSU4.BusinessSignUp4
import com.example.trippleatt.util.log
import com.example.trippleatt.util.toast
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class BusinessSignUp3 : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private val factory: BusinessSU3VMF by instance()

    private lateinit var binding: ActivityBusinessSignUp3Binding
    private lateinit var viewModel: BusinessSU3VM
    private lateinit var view: View

    private var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBusinessSignUp3Binding.inflate(layoutInflater)
        view = binding.root
        viewModel = ViewModelProvider(this, factory).get(BusinessSU3VM::class.java)
        setContentView(view)

        val intent = intent

        email = intent.getStringExtra("email").toString()

        binding.btnCreateBusinessAccount.setOnClickListener {
            createAccount()
        }

    }

    private fun createAccount(){

        val password = binding.etPassword.text.toString().trim()
        val cPassword = binding.etConfirmPassword.text.toString().trim()

        if (password.length < 6){
            binding.etPassword.error = "Password too short."
            return
        }

        if (password != cPassword){
            binding.etConfirmPassword.error = "Password don't match."
            return
        }

        viewModel.createUserAccount(email, password)

        viewModel.createUserAccount.observe(this, { result ->
            when(result){
                is Results.Success -> {
                    startActivity(Intent(this, BusinessSignUp4::class.java))
                    finish()
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