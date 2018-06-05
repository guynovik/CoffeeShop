package com.example.guy.projectbagrut;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.tasks.OnSuccessListener;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.text.DecimalFormat;


import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener listener;
    private FusedLocationProviderClient locationProviderClient;
    private LatLng currentLocationLatLng;
    private double lat, lng;

    Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        //locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);




        // get all loactions of stores from db and create markers




        // configure_button();




        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
/*
                if(marker != null)
                {
                    marker.remove();
                }
*/



    //            Toast.makeText(MapsActivity.this, "in locationListner"    , Toast.LENGTH_LONG).show();


                // Add a marker in Sydney and move the camera
              //  LatLng curLoc = new LatLng(location.getLatitude(), location.getLongitude());


                //Convert LatLng to Location
                Location storeLocation = new Location("Test");
                location.setLatitude(lat);
                location.setLongitude(lng);

                int dist = (int)storeLocation.distanceTo(location);

                Toast.makeText(MapsActivity.this, "dist is:" +dist   , Toast.LENGTH_LONG).show();

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        configure_button();
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


        Intent i = getIntent();
        lng = i.getDoubleExtra("lng", 0.0);
        lat = i.getDoubleExtra("lat", 0.0);
        String name = i.getStringExtra("name");

       // Toast.makeText(MapsActivity.this, "lng:"+lng+", lat:" +lat+", name:" +name    , Toast.LENGTH_LONG).show();

        LatLng store = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions()
                .position(store)
                .title(name));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(store, 10.2f));

/*
        int cnt=0;
        for (Stores s : stores)
        {
            // Add a marker and move the camera
            curStore = new LatLng(s.lat, s.lon);


            // googleMap.addMarker(new MarkerOptions().title("New Marker").position(yourGivenPosition).icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap("your drawable name",72,64))));

            //   mMap.addMarker(new MarkerOptions().position(curStore).title(""+s.storeName).icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap("marker.png",20,20))));

            if (s.storeName.equals(NaviActivity.storeName)) // store is destination
                mMap.addMarker(new MarkerOptions().position(curStore).title(""+s.storeName).icon(BitmapDescriptorFactory.fromResource(R.drawable.markerdest)));
            else
                mMap.addMarker(new MarkerOptions().position(curStore).title(""+s.storeName).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
            //mMap.addMarker(new MarkerOptions().position(curStore).title(""+s.storeName));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curStore, 100.0f));

            cnt++;
        }
*/
        //   Toast.makeText(NaviMapActivity.this, "cnt:"+cnt      , Toast.LENGTH_LONG).show();




        /*
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10.2f));
*/
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }

        locationManager.requestLocationUpdates("gps", 50, 10, listener);

    }
    /*
        public Bitmap resizeBitmap(String drawableName,int width, int height){
            Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(drawableName, "drawable", getPackageName()));
            return Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        }
    */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    void configure_button()
    {
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }

    }
}
