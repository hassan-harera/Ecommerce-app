package com.harera.choose_location

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.harera.choose_location.databinding.ActivityChooseLocationBinding
import com.harera.common.base.BaseActivity
import com.harera.common.onSearchConfirmed
import com.harera.common.navigation.utils.Arguments
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException


@AndroidEntryPoint
class ChooseLocationActivity : BaseActivity(), OnMapReadyCallback {

    private val DEFAULT_ZOOM = 15f
    private var lastKnownLocation: Location? = null
    private lateinit var map: GoogleMap
    private lateinit var position: LatLng
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var placesClient: PlacesClient
    private val LOC_REQUST_CODE = 3004

    private lateinit var bind: ActivityChooseLocationBinding
    private val chooseLocationViewModel: ChooseLocationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityChooseLocationBinding.inflate(layoutInflater)
        setContentView(bind.root)

        setupMap()
        setupListener()
        setupObserver()
    }

    private fun setupMap() {
        Places.initialize(applicationContext, getString(R.string.google_maps_key))
        placesClient = Places.createClient(this)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun setupObserver() {
        chooseLocationViewModel.location.observe(this) {
            position = it
        }
    }

    private fun setupListener() {
        bind.searchLocation.onSearchConfirmed {
            searchLocation(it)
        }
    }

    private fun verifyLocationPermission() {
        if (checkLocationPermission()) {
            updateLocationUI()
        } else {
            requestLocationPermission()
        }
    }

    private fun checkLocationPermission() =
        ContextCompat.checkSelfPermission(
            this.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOC_REQUST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOC_REQUST_CODE -> {
                if (grantResults.isEmpty() ||
                    grantResults[0] == PackageManager.PERMISSION_DENIED
                ) {
                    onRequestDenied()
                } else {
                    updateLocationUI()
                }
            }
        }
    }

    private fun onRequestDenied() {
        Toast.makeText(this, R.string.location_not_accessed, Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun updateLocationUI() {
        try {
            map.isMyLocationEnabled = true
            map.uiSettings.isMyLocationButtonEnabled = true
            map.uiSettings.isZoomControlsEnabled = true
            getDeviceLocation()
            setupMapListener()
        } catch (e: SecurityException) {
            handleFailure(e)
        }
    }

    private fun setupMapListener() {
        map.setOnMapClickListener {
            chooseLocationViewModel.setLocation(it)
            updateMapUI()
        }
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        verifyLocationPermission()
    }

    private fun getDeviceLocation() {
        if (!checkLocationEnabled()) {
            goToLocationManager()
        }

        try {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener(this) {

                if (it.isSuccessful) {
                    lastKnownLocation = it.result
                    if (lastKnownLocation != null) {
                        val position =
                            LatLng(lastKnownLocation!!.latitude, lastKnownLocation!!.longitude)
                        chooseLocationViewModel.setLocation(position)
                        updateMapUI()
                    }
                } else {
                    handleFailure(it.exception ?: Exception())
                }
            }
        } catch (e: SecurityException) {
            handleFailure(e)
        }
    }

    private fun goToLocationManager() {
        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }

    private fun checkLocationEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun updateMapUI() {
        map.clear()
        map.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(position, DEFAULT_ZOOM)
        )
        map.addMarker(
            MarkerOptions()
                .position(position)
                .title(getString(R.string.your_location))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
        )
        bind.confirm.isEnabled = true
        bind.confirm.setOnClickListener {
            backWithResult()
        }
        hideSoftKeyboard()
    }

    private fun backWithResult() {
        val returnIntent = Intent()
        returnIntent.putExtra(Arguments.LOCATION_RESULT, position)
        setResult(RESULT_OK, returnIntent)
        finish()
    }

    private fun searchLocation(searchString: String) {
        val geocoder = Geocoder(this)
        var list: List<Address> = ArrayList()
        try {
            list = geocoder.getFromLocationName(searchString, 1)?.toList() ?: emptyList()
        } catch (e: IOException) {
            handleFailure(e)
        }
        if (list.isNotEmpty()) {
            val address = list[0]
            position = LatLng(address.latitude, address.longitude)
            updateMapUI()
        }
    }
}