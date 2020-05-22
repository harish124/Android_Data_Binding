package com.example.annapoorna;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.annapoorna.databinding.ActivityPassengerBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.UUID;

import print.Print;

public class PassengerActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Print p;
    private ActivityPassengerBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private FirebaseDatabase fdb;
    private DatabaseReference mRef;
    private  Location passloc;
    void init(){
        p=new Print(this);
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);

        fdb=FirebaseDatabase.getInstance();
        mRef=fdb.getReference("RequestProduct");




        binding.requestBtn.setOnClickListener((v)->{
            makeRequest(mRef);
        });

    }

    private void makeRequest(DatabaseReference mRef) {
        HashMap<String,String> obj=new HashMap<>();
        obj.put("Lat",""+passloc.getLatitude());
        obj.put("Long",""+passloc.getLongitude());

        mRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(UUID.randomUUID().toString())
                .setValue(obj).addOnSuccessListener((aVoid)->{
                    p.sprintf("Request Made Successfully!");
        }).addOnFailureListener((exception)->{
            p.fprintf("Error in making Request!\nError: "+exception.getMessage());
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_passenger);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        init();
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

        locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);

        locationListener= new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                configureMarker(new LatLng(location.getLatitude(),location.getLongitude()));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if(Build.VERSION.SDK_INT<23)
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    0,0,locationListener);
        }
        else if(Build.VERSION.SDK_INT>=23)
        {
            if(ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                },1000);
            }
            else {
                if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                        ==PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            0,0,locationListener);

                    getLastLocation();
                    //p.sprintf("Lat = "+location.getLatitude());

                }
            }
        }

    }

    private void configureMarker(LatLng obj) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(obj);
        markerOptions.title("You are here!");
        //markerOptions.snippet("This is awesome");
        markerOptions.snippet(obj.latitude + "," + obj.longitude);


        CircleOptions circleOptions = new CircleOptions();
        circleOptions.strokeColor(Color.BLUE);
        circleOptions.radius(10);
        circleOptions.center(obj);
        circleOptions.strokeWidth(20.0f);

        mMap.addMarker(markerOptions);
        mMap.addCircle(circleOptions);

        CameraUpdate cameraUpdate=CameraUpdateFactory.newLatLngZoom(obj,17.25f);
        mMap.moveCamera(cameraUpdate);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==1000)
        {
            if(grantResults.length>0)
            {
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {

                    if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                            ==PackageManager.PERMISSION_GRANTED) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                0,0,locationListener);

                        getLastLocation();
                    }
                }
            }
        }
    }

    void getLastLocation()
    {

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location!=null) {
                    passloc=location;
                    configureMarker(new LatLng(location.getLatitude(),location.getLongitude()));
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                p.fprintf("Failed to retrieve Locaiton\nError: "+e.getMessage());
            }
        });
    }


}
