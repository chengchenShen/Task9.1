package com.example.task91;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PlaceActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapFragment mapFragment;
    Double lats,lons;
    String name , lat,lon;
    LatLng latLng , nc, th, sb;
    int temp = 0, temp2 = 0, temp3 = 0;
    Intent intent;
    public Double[] latt = new Double[10];
    public Double[] lont = new Double[10];
    public String[] namet = new String[10];


    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(PlaceActivity.this);

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        intent = getIntent();
        setIntent(intent);

/*        lat = intent.getStringExtra("lat");
        lon = intent.getStringExtra("lon");
        name = intent.getStringExtra("place_name");
        lats = (double) Double.parseDouble(lat);
        lons = (double) Double.parseDouble(lon);*/
/*        latt[temp] = lats;
        lont[temp2] = lons;
        namet[temp3] = name;
        temp++;
        temp2++;
        temp3++;*/

/*        for (int a = 0; latt[a]!=null; a++) {
            latLng = new LatLng(latt[a], lont[a]);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8.5f));

            mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(namet[a])
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }*/


        nc  = new LatLng(-37.87497078594508, 145.16585412729106);
        th  = new LatLng(-37.835585934877564, 145.16642651310596);
        sb  = new LatLng(-37.85326483313456, 145.1604210815846);
        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8.5f));

        mMap.addMarker(new MarkerOptions()
                .position(nc)
                .title("Nene Chicken The Glen")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        mMap.addMarker(new MarkerOptions()
                .position(th)
                .title("Tasty House")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        mMap.addMarker(new MarkerOptions()
                .position(sb)
                .title("Sofia Burwood Italian Pizza Restaurant")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }

}