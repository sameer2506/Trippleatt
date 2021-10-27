package com.example.trippleatt.data

import android.app.Activity
import android.content.Context
import android.net.Uri
import com.example.trippleatt.AppPreferences
import com.example.trippleatt.util.log
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class Repository(context: Context) : DataSource {

    private var verificationCode: String = ""

    private val auth: FirebaseAuth = Firebase.auth

    private lateinit var storageReference: StorageReference

    private val firestoreDatabase: FirebaseFirestore = FirebaseFirestore.getInstance()

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
                Results.Success(true)
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
                Results.Error(e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    log("FirebaseAuthInvalidCredentialsException: Invalid request")
                    Results.Error(e)
                } else if (e is FirebaseTooManyRequestsException) {
                    log("FirebaseTooManyRequestsException: The SMS quota for the project has been exceeded")
                    Results.Error(e)
                }
            }

        }

    override suspend fun sendOtp(phoneNumber: String, activity: Activity): Results<Boolean> =
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

    override suspend fun signInWithEmail(email: String, password: String): Results<Boolean> =
        suspendCoroutine { cont ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    cont.resume(Results.Success(true))
                }
                .addOnFailureListener {
                    cont.resume(Results.Error(it))
                }
        }

    // Business Sign Up 3 Activity

    override suspend fun createUserAccount(email: String, password: String): Results<Boolean> =
        suspendCoroutine { cont ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    cont.resume(Results.Success(true))
                }
                .addOnFailureListener {
                    cont.resume(Results.Error(it))
                }
        }

    // Upload Image

    override suspend fun uploadImage(data: Uri, rnds: Int): Results<FileLink> =
        suspendCoroutine {
            storageReference = FirebaseStorage.getInstance().getReference("$rnds")

            val uploadTask = storageReference.putFile(data)
            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    throw task.exception!!
                }
                storageReference.downloadUrl
            }.addOnCompleteListener { task ->
                val data = FileLink(task.result.toString())
                Results.Success(data)
            }
        }

    // Saving Business Details

    override suspend fun saveBusinessDetails(
        data: HashMap<String, Any>
    ): Results<Boolean> =
        suspendCoroutine { cont ->
            firestoreDatabase
                .collection("details")
                .document()
                .set(data)
                .addOnSuccessListener {
                    cont.resume(Results.Success(true))
                }
                .addOnFailureListener {
                    cont.resume(Results.Error(it))
                }
        }

    override suspend fun getShopList(): Results<List<ShopListData>> =
        suspendCoroutine { cont ->
            val query = firestoreDatabase
                .collection("details")

            query
                .get()
                .addOnSuccessListener { queryList ->
                    val shopList: List<ShopListData> =
                        queryList.toObjects(ShopListData::class.java) as List<ShopListData>
                    cont.resume(Results.Success(shopList))
                }
                .addOnFailureListener {
                    cont.resume(Results.Error(it))
                }
        }


}