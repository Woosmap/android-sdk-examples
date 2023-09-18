package com.woosmap.searchlocalities;

import androidx.appcompat.app.AppCompatActivity;

import com.woosmap.sdk.LocalitiesAutocompleteTextView;
import com.woosmap.sdk.MapView;
import com.woosmap.sdk.Woosmap;
import com.woosmap.sdk.api.LocalitiesService;
import com.woosmap.sdk.api.localities.LocalitiesAutocompleteResponse;
import com.woosmap.sdk.geometry.LatLng;
import com.woosmap.sdk.maps.camera.CameraUpdate;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MapView mapView;
    private LocalitiesAutocompleteTextView autoComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView = findViewById(R.id.mapView);
        mapView.setZoomLevel(3);
        mapView.setCenter(new LatLng(47.7724413, 12.4833043));

        autoComplete = findViewById(R.id.autoComplete);
        autoComplete.setOnLocalitiesSelected(locality -> {
            CameraUpdate cameraUpdate;
            if (locality.getGeometry().getViewport() != null) {
                cameraUpdate = CameraUpdate.toBounds(locality.getGeometry().getViewport());
            } else {
                cameraUpdate = CameraUpdate.toTarget(locality.getGeometry().getLocation());
            }
            mapView.easeCamera(cameraUpdate);
            mapView.requestFocus();
            return null;
        });
    }
}

class MyView extends View {
    private LocalitiesService localitiesService = new LocalitiesService();
    private TextView someTextView;

    MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Woosmap.initialize(context);
    }

    protected void exampleOfLocalitiesAutocompleteQuery(String input) {
        new Thread(() -> {
            List<LocalitiesAutocompleteResponse> localities = localitiesService.localitiesAutocomplete(input);
            localities.forEach(locality -> System.out.println(locality.getDescription()));
            post(() -> {
                if (localities.size() > 0) {
                    someTextView.setText(localities.get(0).getDescription());
                }
            });
        });
    }
}