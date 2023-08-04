package com.woosmap.simplemap;

import androidx.appcompat.app.AppCompatActivity;
import com.woosmap.sdk.MapView;
import com.woosmap.sdk.geometry.LatLng;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private MapView MapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapView = findViewById(R.id.mapView);
        MapView.setZoomLevel(3);
        MapView.setCenter(new LatLng(47.7724413, 12.4833043));
    }
}