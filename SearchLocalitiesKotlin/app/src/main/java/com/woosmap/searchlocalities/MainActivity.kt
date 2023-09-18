package com.woosmap.searchlocalities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.woosmap.sdk.LocalitiesAutocompleteTextView
import com.woosmap.sdk.MapView
import com.woosmap.sdk.geojson.Feature
import com.woosmap.sdk.geojson.Point
import com.woosmap.sdk.geojson.Polygon
import com.woosmap.sdk.geometry.LatLng
import com.woosmap.sdk.geometry.toLineString
import com.woosmap.sdk.maps.camera.CameraUpdate
import com.woosmap.sdk.maps.renderer.DataRenderer

class MainActivity : AppCompatActivity() {

    private lateinit var mapView: MapView
    private lateinit var autoComplete: LocalitiesAutocompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapView = findViewById(R.id.mapView)
        mapView.zoomLevel = 3.0
        mapView.center = LatLng(47.7724413, 12.4833043)

        autoComplete = findViewById(R.id.autoComplete)
        autoComplete.onLocalitiesSelected = { locality ->
            val cameraUpdate = when {
                locality.geometry.viewport != null -> {
                    CameraUpdate(bounds = locality.geometry.viewport)
                }
                else -> {
                    CameraUpdate(target=locality.geometry.location)
                }
            }
            mapView.easeCamera(cameraUpdate)
            mapView.requestFocus()
        }
    }
}