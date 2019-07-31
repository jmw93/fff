package com.example.fragment;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

public class MapsActivity extends AppCompatActivity {



    TextView textView;
    String emailAddr;
    String emailPassword;
    String recipient;
    LocationRequest request;
    EditText edit_Id;
    EditText edit_password;
    EditText edit_recipient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        edit_Id =findViewById(R.id.edit_Id);
        edit_password =findViewById(R.id.edit_Password);
        edit_recipient =findViewById(R.id.edit_recipient);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        Button btn_stop = findViewById(R.id.btn_stop);
        Button btn_send = findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onstartService();
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onstopService();
            }
        });


    }

    private void onstartService() {
        emailAddr = edit_Id.getText().toString();
        emailPassword = edit_password.getText().toString();
        recipient = edit_recipient.getText().toString();

        Intent intent= new Intent(this,MyService.class);
        intent.putExtra("emailAddr",emailAddr);
        intent.putExtra("emailPassword",emailPassword);
        intent.putExtra("recipient",recipient);

        startService(intent);
    }

    private void onstopService() {
        Intent intent= new Intent(this,MyService.class);
        stopService(intent);
    }

}
