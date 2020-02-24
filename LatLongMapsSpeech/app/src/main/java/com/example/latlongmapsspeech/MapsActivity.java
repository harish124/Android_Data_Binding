package com.example.latlongmapsspeech;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.example.latlongmapsspeech.model.Country;
import com.example.latlongmapsspeech.print.Print;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String receivedCountry;
    private Print p;

    void init() {
        Intent intent = this.getIntent();
        receivedCountry = intent.getStringExtra(Country.countryKey);
        if (receivedCountry == null) {
            receivedCountry = Country.countryName;
        }
        p = new Print(MapsActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        init();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Geocoder geocoder = new Geocoder(MapsActivity.this);
        //PVR SkyWalk   13.073843, 80.221022 default
        double countryLat = 13.073843;
        double countryLong = 80.221022;
        Country myCountry = MainActivity.country;
        String countryMsg = myCountry.getCountryInfo(receivedCountry);


        try {
            List<Address> countryAddresses = geocoder.getFromLocationName(receivedCountry, 10);
            if (countryAddresses != null) {
                countryLat = countryAddresses.get(0).getLatitude();
                countryLong = countryAddresses.get(0).getLongitude();
            } else {
                p.fprintf("Country/Address Not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        LatLng skyWalk = new LatLng(countryLat, countryLong);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(skyWalk, 5.2f);
        mMap.moveCamera(cameraUpdate);


        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(skyWalk);
        markerOptions.title(countryMsg);
        //markerOptions.snippet("This is awesome");
        markerOptions.snippet(countryLat + "," + countryLong);


        CircleOptions circleOptions = new CircleOptions();
        circleOptions.strokeColor(Color.BLUE);
        circleOptions.radius(200);
        circleOptions.center(skyWalk);
        circleOptions.strokeWidth(20.0f);

        mMap.addMarker(markerOptions);
        mMap.addCircle(circleOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(skyWalk));
    }
}
