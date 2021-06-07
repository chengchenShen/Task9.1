package com.example.task91;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button Add, Show;
    String name , lat,lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Add = findViewById(R.id.Add);
        Show = findViewById(R.id.Show);
    }

    public void AddNewPlace(View view) {

        Intent intent = new Intent();
        intent.setClass(MainActivity.this,Map.class);
        startActivity(intent);

    }

    public void ShowAllOnMap(View view) {
        Intent intent =getIntent();
        intent.setClass(MainActivity.this,PlaceActivity.class);
        startActivity(intent);
    }
}