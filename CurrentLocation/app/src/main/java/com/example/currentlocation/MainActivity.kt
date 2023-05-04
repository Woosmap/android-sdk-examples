package com.example.currentlocation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.woosmap.woosmapsdk.Icon
import com.woosmap.woosmapsdk.Marker
import com.woosmap.woosmapsdk.Size
import com.woosmap.woosmapsdk.WoosmapView
import com.woosmap.woosmapsdk.client.models.LatLngLiteral


class MainActivity : AppCompatActivity() {

    lateinit var mapView: WoosmapView
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationPermissionRequest: ActivityResultLauncher<Array<String>>
    private lateinit var _currentLocationMarker: Marker
    private lateinit var currentLocation: LatLngLiteral

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ask for location permissions
        locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                    Log.d("Distance", "fine loc granted")
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                    Log.d("Distance", "coarse loc granted")
                }
                else -> {
                    // No location access granted.
                    Log.d("Distance", "no bueno loc DENIIIIEEEEED")
                    locationPermissionRequest.unregister()
                }
            }
        }

        setContentView(R.layout.activity_main)
        mapView = findViewById<WoosmapView>(R.id.mapView)
        //mapView.zoomLevel = 8.0
        getLocationUpdates()
        startLocationUpdates()
    }

    private fun getLocationUpdates() {
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(applicationContext)
        locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 5000
        ).build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult.locations.isNotEmpty()) {
                    locationResult.lastLocation?.let {
                        currentLocation = LatLngLiteral(it.latitude, it.longitude)
                        mapView.center = currentLocation
                        mapView.zoomLevel = 8.0
                        updateMarker(currentLocation)
                    }
                }
            }
        }
    }

    private fun startLocationUpdates() {
        when {
            ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
                fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.getMainLooper()
                )
            }
            /*
            shouldShowRequestPermissionRationale(...) -> {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected, and what
                // features are disabled if it's declined. In this UI, include a
                // "cancel" or "no thanks" button that lets the user continue
                // using your app without granting the permission.
                showInContextUI(...)
            }
            */
            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                locationPermissionRequest.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }
    }

    private fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    private fun updateMarker(currentLocation: LatLngLiteral) {
        mapView?.let { mapView ->
            if (this::_currentLocationMarker.isInitialized) {
                _currentLocationMarker.position = currentLocation
            } else {
                _currentLocationMarker = Marker(
                    currentLocation,
                    Icon(
                        "https://images.woosmap.com/dot-marker.png",
                        scaledSize = Size(width = 46, height = 64)
                    )
                )
                mapView.addMarker(_currentLocationMarker)
            }
        }
    }
}
