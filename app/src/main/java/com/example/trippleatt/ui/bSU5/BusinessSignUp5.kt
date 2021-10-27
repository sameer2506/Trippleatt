package com.example.trippleatt.ui.bSU5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trippleatt.data.Results
import com.example.trippleatt.databinding.ActivityBussinessSignUp5Binding
import com.example.trippleatt.ui.bSU6.BusinessSignUp6
import com.example.trippleatt.ui.otpV2.OtpVerification2
import com.example.trippleatt.util.log
import com.example.trippleatt.util.toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.location.places.Place
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

import com.google.android.gms.location.places.ui.PlaceSelectionListener

import com.google.android.gms.common.api.Status

import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment

class BusinessSignUp5 : AppCompatActivity(),
    OnMapReadyCallback,
    ShopListAdapter.OnItemClick,
    KodeinAware {

    override val kodein by kodein()

    private val factory: BusinessSU5VMF by instance()

    private lateinit var viewModel: BusinessSU5VM
    private lateinit var binding: ActivityBussinessSignUp5Binding

    private lateinit var shopListAdapter: ShopListAdapter

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBussinessSignUp5Binding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, factory).get(BusinessSU5VM::class.java)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(com.example.trippleatt.R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val autocompleteFragment =
            fragmentManager.findFragmentById(com.example.trippleatt.R.id.place_autocomplete_fragment) as PlaceAutocompleteFragment

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                mMap.clear()
                mMap.addMarker(MarkerOptions().position(place.latLng).title(place.name.toString()))
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place.latLng))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.latLng, 12.0f))
            }

            override fun onError(status: Status?) {}
        })

        viewModel.getShopList()

        viewModel.getShopList.observe(this, {
            when(it){
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
            startActivity(Intent(this, BusinessSignUp6::class.java))
            finish()
        }

    }

    override fun onItemClick(position: Int) {
        val id = shopListAdapter.getItemId(position)
        val intent = Intent(this, OtpVerification2::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
        finish()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}