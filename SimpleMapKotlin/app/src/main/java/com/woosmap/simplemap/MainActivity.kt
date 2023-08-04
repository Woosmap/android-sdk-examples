package com.woosmap.simplemap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.woosmap.sdk.MapView
import com.woosmap.sdk.geometry.LatLng

class MainActivity : AppCompatActivity() {
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapView = findViewById(R.id.mapView)
        mapView.zoomLevel = 3.0
        mapView.center = LatLng(47.7724413, 12.4833043)
    }
}