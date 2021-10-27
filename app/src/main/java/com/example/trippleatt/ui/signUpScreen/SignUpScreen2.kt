package com.example.trippleatt.ui.signUpScreen

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.trippleatt.AppPreferences
import com.example.trippleatt.R
import com.example.trippleatt.databinding.ActivityBussinessSignUp5Binding
import com.example.trippleatt.databinding.ActivitySignUpScreen2Binding
import com.example.trippleatt.ui.bSU5.BusinessSU5VM
import com.example.trippleatt.ui.bSU5.BusinessSU5VMF
import com.example.trippleatt.ui.bSU5.ShopListAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import android.location.Geocoder
import com.example.trippleatt.WelcomeScreen
import com.example.trippleatt.data.Results
import com.example.trippleatt.util.log
import com.example.trippleatt.util.toast
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener

import com.google.android.gms.maps.model.Marker
import java.util.*


class SignUpScreen2 : AppCompatActivity(), OnMapReadyCallback, KodeinAware {

    override val kodein by kodein()

    private val factory: SignUpScrVMF by instance()

    private lateinit var viewModel: SignUpScrVM
    private lateinit var binding: ActivitySignUpScreen2Binding

    private lateinit var shopListAdapter: ShopListAdapter

    private lateinit var appPreferences: AppPreferences

    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101

    private var currAddress: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpScreen2Binding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, factory).get(SignUpScrVM::class.java)
        setContentView(binding.root)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)
        fetchLocation()

        binding.btnSaveAndContinue.setOnClickListener {
            saveDetails()
        }

    }

    private fun saveDetails(){
        val add = "${binding.etBlockNo.text} ${binding.etLandmark.text} $currAddress"

        val intent = intent

        val fName = intent.getStringExtra("fName")
        val lName = intent.getStringExtra("lName")

        val data: HashMap<String, Any> = hashMapOf()

        data["fName"] = fName!!
        data["lName"] = lName!!
        data["address"] = add

        viewModel.saveUserDetails(data)

        viewModel.saveUserDetails.observe(this, {
            when (it) {
                is Results.Success -> {
                    startActivity(Intent(this, WelcomeScreen::class.java))
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

    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), permissionCode
            )
            return
        }
        val task = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener {
            if (it != null) {
                currentLocation = it
                val supportMapFragment = (supportFragmentManager.findFragmentById(R.id.map) as
                        SupportMapFragment?)!!
                supportMapFragment.getMapAsync(this)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        val markerOptions = MarkerOptions().position(latLng).title("You are here!")

        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses = geocoder.getFromLocation(currentLocation.latitude, currentLocation.longitude, 1)

        val address = addresses[0]
        currAddress = address.getAddressLine(0)

        binding.tvAddress.text = currAddress
        googleMap?.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
        googleMap?.addMarker(markerOptions)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            permissionCode -> if (grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED
            ) {
                fetchLocation()
            }
        }
    }
}