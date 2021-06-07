package com.example.task91;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Map extends AppCompatActivity  implements LocationListener{

    private GoogleMap mMap;
    Button Current_Location, Show_On_Map, Save;
    SupportMapFragment supportMapFragment;
    MapResult mapResult;
    FusedLocationProviderClient fusedLocationProviderClient;
    EditText current_place, latitude, longitude, etplace;
    TextView address;
    String  addresses, locationName, a , name;
    Double lat,lon;
    AutocompleteSupportFragment autocompleteFragment;
    LocationManager locationManager;
    LatLng latLng;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Current_Location = findViewById(R.id.Current_location);
        Show_On_Map = findViewById(R.id.Show_on_map);
        Save = findViewById(R.id.Save);
        etplace = findViewById(R.id.Place_name);
        address = findViewById(R.id.address);
        //latitude = findViewById(R.id.Latitude);
        //longitude = findViewById(R.id.longitude);



        // Initialize the SDK
        Places.initialize(getApplicationContext(), getString(R.string.map_key));

        // Create a new PlacesClient instance
        PlacesClient placesClient = Places.createClient(this);
        // Initialize the AutocompleteSupportFragment.
        autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS));
        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                Toast.makeText(Map.this,"Place: " + place.getName() + ", " + place.getId() + ", "+place.getAddress() + " "+ place.getLatLng(), Toast.LENGTH_LONG).show();
                etplace.setText(place.getName());
                a = place.getAddress();
                lat = place.getLatLng().latitude;
                lon = place.getLatLng().longitude;
                name = place.getName();
                latLng = place.getLatLng();
/*
                Bundle bundle = new Bundle();
                bundle.putString("name",name);
                bundle.putDouble("lat",lat);
                bundle.putDouble("lon",lon);*/
            }
            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
            }
        });

        Show_On_Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Mapintent = new Intent(Map.this,MapResult.class);
                Mapintent.putExtra("lat", lat.toString());
                Mapintent.putExtra("lon",lon.toString());
                Mapintent.putExtra("place_name", name);
                startActivity(Mapintent);
            }
        });


        if(ActivityCompat.checkSelfPermission(Map.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //getCurrentLocation();
        }else{
            ActivityCompat.requestPermissions(Map.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100);
        }
        
        Current_Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etplace.length() == 0){
                    getLocation();
                }else{
                    autocompleteFragment.setText(a);
                }

            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent saveIntent = new Intent(Map.this, MainActivity.class);
                saveIntent.putExtra("lat", lat.toString());
                saveIntent.putExtra("lon",lon.toString());
                saveIntent.putExtra("place_name", name);
                startActivity(saveIntent);

            }
        });

    }


@SuppressLint("MissingPermission")
    private void getLocation() {
        try{
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, Map.this);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(this, "" + location.getLatitude() + "," + location.getLongitude(), Toast.LENGTH_LONG).show();

        try{
            Geocoder geocoder = new Geocoder(Map.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String addresss = addresses.get(0).getAddressLine(0);
            //address.setText(addresss);
            etplace.setText("User current place");
            autocompleteFragment.setText(addresss);


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    private class GeoHandler extends Handler {

        @Override
        public void handleMessage(@NonNull Message msg) {
            String address2;
            switch (msg.what){
                case 1:
                    Bundle bundle = msg.getData();
                    address2 = bundle.getString("address");
                    break;
                default:
                    address2 = null;
            }
            address.setText(address2);
        }
    }
}