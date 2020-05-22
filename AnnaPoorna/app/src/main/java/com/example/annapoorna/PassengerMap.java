package com.example.annapoorna;

import android.Manifest;
import android.app.ActionBar;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.annapoorna.databinding.ActivityPassengerBinding;
import com.example.annapoorna.databinding.FragmentPassengerMapBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
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

import static android.content.Context.LOCATION_SERVICE;


public class PassengerMap extends Fragment {

    private View pview;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Print p;
    private FragmentPassengerMapBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private FirebaseDatabase fdb;
    private DatabaseReference mRef;
    private Location passloc;
    void init(View v){
        p=new Print(v.getContext());
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(v.getContext());

        fdb=FirebaseDatabase.getInstance();
        mRef=fdb.getReference("RequestProduct");




        binding.requestBtn.setOnClickListener((btnView)->{
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
    public PassengerMap() {
        // Required empty public constructor
    }


    void hideBottomAppBar() {
        ActionBar ab=getActivity().getActionBar();
        if(ab!=null)
        {
            ab.hide();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //pview=inflater.inflate(R.layout.fragment_passenger_map, container, false);

        hideBottomAppBar();
        binding= DataBindingUtil.inflate
                (inflater,R.layout.fragment_passenger_map,container,false);
        pview=binding.getRoot();
        init(pview);
        hideBottomAppBar();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync((googleMap)->{
            //onMapReady
            mMap = googleMap;


            locationManager=(LocationManager)pview.getContext().getSystemService(LOCATION_SERVICE);

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
                if(ContextCompat.checkSelfPermission(pview.getContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },1000);
                }
                else {
                    if (ContextCompat.checkSelfPermission(pview.getContext(),Manifest.permission.ACCESS_FINE_LOCATION)
                            ==PackageManager.PERMISSION_GRANTED) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                0,0,locationListener);

                        getLastLocation();
                        //p.sprintf("Lat = "+location.getLatitude());

                    }
                }
            }

        });

        return pview;
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

        CameraUpdate cameraUpdate= CameraUpdateFactory.newLatLngZoom(obj,17.25f);
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

                    if (ContextCompat.checkSelfPermission(pview.getContext(),Manifest.permission.ACCESS_FINE_LOCATION)
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
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener((location)->{
            if (location!=null) {
                passloc=location;
                configureMarker(new LatLng(location.getLatitude(),location.getLongitude()));
            }
        }).addOnFailureListener((e)-> {
            p.fprintf("Failed to retrieve Locaiton\nError: " + e.getMessage());
        });
    }

}
