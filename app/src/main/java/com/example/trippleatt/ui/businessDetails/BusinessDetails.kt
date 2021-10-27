package com.example.trippleatt.ui.businessDetails

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.trippleatt.WelcomeScreen
import com.example.trippleatt.data.Results
import com.example.trippleatt.databinding.ActivityBusinessDetailsBinding
import com.example.trippleatt.util.log
import com.example.trippleatt.util.toast
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class BusinessDetails : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private val factory: BusinessDetailsVMF by instance()

    private lateinit var binding: ActivityBusinessDetailsBinding
    private lateinit var viewModel: BusinessDetailsVM
    private lateinit var view: View

    private lateinit var storageReference: StorageReference

    private var frontImageLink: String? = null
    private var image1Link: String? = null
    private var image2Link: String? = null

    companion object {
        const val PICK_IMAGE_CODE = 1000
        const val PICK_IMAGE_CODE1 = 1001
        const val PICK_IMAGE_CODE2 = 1002
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBusinessDetailsBinding.inflate(layoutInflater)
        view = binding.root
        viewModel = ViewModelProvider(this, factory).get(BusinessDetailsVM::class.java)
        setContentView(view)

        binding.frontImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_CODE)
        }

        binding.image1.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_CODE1)
        }

        binding.image2.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_CODE2)
        }

        binding.btnSubmit.setOnClickListener {
            submitDetails()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            PICK_IMAGE_CODE -> {
                val pd = ProgressDialog(this)
                pd.setTitle("Uploading image...")
                pd.show()
                binding.frontImage.isEnabled = false

                val rnds = (0..10000).random()
                viewModel.uploadImage(data?.data!!, rnds)

                viewModel.uploadImage.observe(this, {
                    when (it) {
                        is Results.Success -> {
                            frontImageLink = it.data.link
                            Picasso.get().load(frontImageLink).into(binding.frontImage)
                        }
                        is Results.Error -> {
                            val builder = AlertDialog.Builder(this)
                            builder.setTitle("Error")
                            builder.setMessage("Image is too large")
                            builder.setPositiveButton("Ok") { _, _ -> }
                            val alertDialog: AlertDialog = builder.create()
                            alertDialog.setCancelable(false)
                            alertDialog.show()
                        }
                        is Results.Loading -> {
                        }
                    }
                })
                pd.dismiss()
            }
            PICK_IMAGE_CODE1 -> {
                val pd = ProgressDialog(this)
                pd.setTitle("Uploading image...")
                pd.show()
                binding.image1.isEnabled = false

                val rnds = (0..10000).random()
                viewModel.uploadImage(data?.data!!, rnds)

                viewModel.uploadImage.observe(this, {
                    when (it) {
                        is Results.Success -> {
                            image1Link = it.data.link
                            Picasso.get().load(image1Link).into(binding.frontImage)
                        }
                        is Results.Error -> {
                            val builder = AlertDialog.Builder(this)
                            builder.setTitle("Error")
                            builder.setMessage("Image is too large")
                            builder.setPositiveButton("Ok") { _, _ -> }
                            val alertDialog: AlertDialog = builder.create()
                            alertDialog.setCancelable(false)
                            alertDialog.show()
                        }
                        is Results.Loading -> {
                        }
                    }
                })
                pd.dismiss()
            }
            PICK_IMAGE_CODE2 -> {
                val pd = ProgressDialog(this)
                pd.setTitle("Uploading image...")
                pd.show()
                binding.image2.isEnabled = false
                val rnds = (0..10000).random()
                viewModel.uploadImage(data?.data!!, rnds)

                viewModel.uploadImage.observe(this, {
                    when (it) {
                        is Results.Success -> {
                            image2Link = it.data.link
                            Picasso.get().load(image2Link).into(binding.frontImage)
                        }
                        is Results.Error -> {
                            val builder = AlertDialog.Builder(this)
                            builder.setTitle("Error")
                            builder.setMessage("Image is too large")
                            builder.setPositiveButton("Ok") { _, _ -> }
                            val alertDialog: AlertDialog = builder.create()
                            alertDialog.setCancelable(false)
                            alertDialog.show()
                        }
                        is Results.Loading -> {
                        }
                    }
                })
                pd.dismiss()
            }
        }
    }

    fun submitDetails() {

        val shopName = binding.etShopName.text.toString().trim()
        val category = binding.etAddCategory.text.toString().trim()
        val contactEmail = binding.etContactEmailId.text.toString().trim()
        val website = binding.etWebsite.text.toString().trim()

        val data: HashMap<String, Any> = hashMapOf()

        data["shopName"] = shopName
        data["category"] = category
        data["contactEmail"] = contactEmail
        data["website"] = website
        data["frontImageLink"] = frontImageLink.toString()
        data["image1Link"] = image1Link.toString()
        data["image2Link"] = image2Link.toString()

        viewModel.saveBusinessDetails(data)

        viewModel.saveBusinessDetails.observe(this, {
            when (it) {
                is Results.Success -> {
                    startActivity(Intent(this, WelcomeScreen::class.java))
                    finish()
                }
                is Results.Error -> {
                    log("signInWithEmail failure: ${it.exception.localizedMessage}")
                    toast("Try again later.")
                }
                is Results.Loading -> {
                }
            }
        })
    }
}