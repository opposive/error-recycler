package com.thiha.hswagata.UserInfo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.thiha.hswagata.R;

import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class Maplocateactivity extends AppCompatActivity implements OnMapReadyCallback {

    MapView mvv;


    GoogleMap gmap;
    FusedLocationProviderClient flpc;
    String startinglatitude = "0.0";
    String startinglongitude = "0.0";

    LatLng lw;

    FirebaseUser u;
    FirebaseAuth fa;
    DatabaseReference drf;
    FirebaseDatabase db;
    Button g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_maplocateactivity );

        ActivityCompat.requestPermissions ( Maplocateactivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED );

        fa = FirebaseAuth.getInstance ();
        u = fa.getCurrentUser ();
        db = FirebaseDatabase.getInstance ();
        drf = db.getReference ( "Users" );

        g.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                /*if (ContextCompat.checkSelfPermission ( getApplicationContext (), Manifest.permission.ACCESS_FINE_LOCATION )!=PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions ( Maplocateactivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
                }
                else{
                    //getCurrentLocation();
                }*/
                Context context;
                flpc = LocationServices.getFusedLocationProviderClient ( Maplocateactivity.this );
                if (ActivityCompat.checkSelfPermission ( Maplocateactivity.this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission ( Maplocateactivity.this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                flpc.getLastLocation ().addOnSuccessListener ( new OnSuccessListener<Location> () {
                    @Override
                    public void onSuccess(Location location) {
                        if (location!=null){
                            startinglatitude = Double.toString (location.getLatitude ());
                            startinglongitude = Double.toString (location.getLongitude ());
                                HashMap<String,Object> result = new HashMap<> (  );
                                result.put ( "Latitude", startinglatitude );
                                result.put ( "Longitude",startinglongitude );
                                drf.child ( u.getUid() ).updateChildren ( result ).addOnSuccessListener ( new OnSuccessListener<Void> () {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText ( Maplocateactivity.this, "success", Toast.LENGTH_SHORT ).show ();
                                    }
                                } ).addOnFailureListener ( new OnFailureListener () {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText ( Maplocateactivity.this, ""+e.getMessage (), Toast.LENGTH_SHORT ).show ();
                                    }
                                } );
                            }

                    }
                } );
            }
        } );

        lw = new LatLng(-34,151 );


    }
/*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult ( requestCode, permissions, grantResults );
        if (requestCode == 1 && grantResults.length>0){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getCurrentLocation ();
            }
            else{
                Toast.makeText ( this, "Permission denied!", Toast.LENGTH_SHORT ).show ();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {

        LocationRequest lr = new LocationRequest();
        lr.setInterval ( 10000 );
        lr.setFastestInterval ( 3000 );
        lr.setPriority ( LocationRequest.PRIORITY_HIGH_ACCURACY );
        LocationServices.getFusedLocationProviderClient ( Maplocateactivity.this )
                .requestLocationUpdates ( lr,new LocationCallback (){

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult ( locationResult );
                        LocationServices.getFusedLocationProviderClient ( Maplocateactivity.this ).removeLocationUpdates ( this );
                        if (locationResult != null && locationResult.getLocations ().size ()>0){
                            int lli = locationResult.getLocations ().size ()-1;
                            Double ltttu = locationResult.getLocations ().get ( lli ).getLatitude ();
                            Double lgggu = locationResult.getLocations ().get ( lli ).getLongitude ();
                            HashMap<String,Object> result = new HashMap<> (  );
                            result.put ( "Latitude",ltttu.toString ().trim () );
                            result.put ( "Longitude",lgggu.toString ().trim () );
                            drf.child ( u.getUid() ).updateChildren ( result ).addOnSuccessListener ( new OnSuccessListener<Void> () {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText ( Maplocateactivity.this, "success", Toast.LENGTH_SHORT ).show ();
                                }
                            } ).addOnFailureListener ( new OnFailureListener () {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText ( Maplocateactivity.this, ""+e.getMessage (), Toast.LENGTH_SHORT ).show ();
                                }
                            } );
                        }
                    }
                },Looper.getMainLooper () );
    }
*/

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize ( this );
        gmap = googleMap;

    }
}