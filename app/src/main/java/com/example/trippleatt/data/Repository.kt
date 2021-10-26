package com.example.trippleatt.data

import android.app.Activity
import android.content.Context
import com.example.trippleatt.AppPreferences
import com.example.trippleatt.util.log
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class Repository(context: Context) : DataSource {

    private var verificationCode: String = ""

    private val auth: FirebaseAuth = Firebase.auth

    private val appPreferences = AppPreferences(context)

    private var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(verificationId, token)

                //Log.d(TAG, "onCodeSent:$verificationId")

                // Save verification ID and resending token so we can use them later
                verificationCode = verificationId
                appPreferences.saveVerificationCode(verificationCode)
                log("Code sent Successfully.")
                Result.Success(true)
            }

            override fun onVerificationCompleted(
                credential: PhoneAuthCredential
            ) {
                val code = credential.smsCode
                if (code != null) {
                    appPreferences.saveCode(code)
                }
                log("verification completed:$credential")
            }

            override fun onVerificationFailed(e: FirebaseException) {
                log("onVerificationFailed:$e")
                Result.Error(e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    log("FirebaseAuthInvalidCredentialsException: Invalid request")
                    Result.Error(e)
                } else if (e is FirebaseTooManyRequestsException) {
                    log("FirebaseTooManyRequestsException: The SMS quota for the project has been exceeded")
                    Result.Error(e)
                }
            }

        }

    override suspend fun sendOtp(phoneNumber: String, activity: Activity): Result<Boolean> =
        suspendCoroutine {
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(5, TimeUnit.MINUTES)
                .setActivity(activity)
                .setCallbacks(mCallbacks)
                .build()

            PhoneAuthProvider.verifyPhoneNumber(options)
        }

    // Business Logic Screen Activity

    override suspend fun signInWithEmail(email: String, password: String): Result<Boolean> =
        suspendCoroutine { cont ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    cont.resume(Result.Success(true))
                }
                .addOnFailureListener {
                    cont.resume(Result.Error(it))
                }
        }

    // Business Sign Up 3 Activity

    override suspend fun createUserAccount(email: String, password: String): Result<Boolean> =
        suspendCoroutine { cont ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    cont.resume(Result.Success(true))
                }
                .addOnFailureListener {
                    cont.resume(Result.Error(it))
                }
        }


}