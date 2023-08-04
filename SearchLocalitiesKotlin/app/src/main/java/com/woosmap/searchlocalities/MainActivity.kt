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

        val dataRenderer = DataRenderer(mapView, onTop = true)

        autoComplete = findViewById(R.id.autoComplete)
        autoComplete.onLocalitiesSelected = { locality ->
            locality.geometry.viewport?.let {
                mapView.easeCamera(CameraUpdate(bounds = it))
                val rectangle = Polygon(
                    listOf(
                        Point(it.longitudeEast, it.latitudeNorth),
                        Point(it.longitudeEast, it.latitudeSouth),
                        Point(it.longitudeWest, it.latitudeSouth),
                        Point(it.longitudeWest, it.latitudeNorth),
                        Point(it.longitudeEast, it.latitudeNorth),
                    )
                )
                dataRenderer.addFeature(Feature(geometry = rectangle)) {
                    strokeColor = "#f00"
                    fillOpacity = 0.0
                }
            }
            mapView.requestFocus()
        }
    }
}