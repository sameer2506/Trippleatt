package com.example.trippleatt.ui.bSU5

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trippleatt.AppPreferences
import com.example.trippleatt.R
import com.example.trippleatt.data.Results
import com.example.trippleatt.databinding.ActivityBussinessSignUp5Binding
import com.example.trippleatt.ui.otpV2.OtpVerification2
import com.example.trippleatt.util.log
import com.example.trippleatt.util.toast
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

class BusinessSignUp5 : AppCompatActivity(),
    OnMapReadyCallback,
    ShopListAdapter.OnItemClick,
    KodeinAware {

    override val kodein by kodein()

    private val factory: BusinessSU5VMF by instance()

    private lateinit var viewModel: BusinessSU5VM
    private lateinit var binding: ActivityBussinessSignUp5Binding

    private lateinit var shopListAdapter: ShopListAdapter

    private lateinit var appPreferences: AppPreferences

    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBussinessSignUp5Binding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, factory).get(BusinessSU5VM::class.java)
        setContentView(binding.root)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)
        fetchLocation()

        appPreferences = AppPreferences(this)

        viewModel.getShopList()

        viewModel.getShopList.observe(this, {
            when (it) {
                is Results.Success -> {
                    shopListAdapter = ShopListAdapter(it.data, this)
                    binding.recyclerVewForShop.layoutManager = LinearLayoutManager(this)
                    binding.recyclerVewForShop.adapter = shopListAdapter
                }
                is Results.Error -> {
                    log("signInWithEmail failure: ${it.exception.localizedMessage}")
                    toast("Authentication failed.")
                }
                is Results.Loading -> {
                }
            }
        })

        binding.cnsAddNewShop.setOnClickListener {
            startActivity(Intent(this, OtpVerification2::class.java))
            finish()
        }

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

    override fun onItemClick(position: Int) {
        val id = shopListAdapter.getItemId(position)
        appPreferences.saveShopId(id)
        val intent = Intent(this, OtpVerification2::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
        finish()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        val markerOptions = MarkerOptions().position(latLng).title("I am here!")
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
        googleMap.addMarker(markerOptions)
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