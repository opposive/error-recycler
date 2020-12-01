package com.thiha.hswagata.UserInfo;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.thiha.hswagata.R;

import java.util.HashMap;

public class Userlocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    LocationManager lm;
    LocationListener ll;
    long mintime = 1000;
    long mindis = 5;
    FirebaseUser u;
    FirebaseAuth fa;
    DatabaseReference drf;
    FirebaseDatabase db;
    Button g;


    LatLng l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_userlocation );
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager ()
                .findFragmentById ( R.id.map );
        mapFragment.getMapAsync ( this );

        fa = FirebaseAuth.getInstance ();
        u = fa.getCurrentUser ();
        db = FirebaseDatabase.getInstance ();
        drf = db.getReference ( "Users" );


        //ActivityCompat.requestPermissions ( Userlocation.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED );

        l = new LatLng ( -34, 151 );

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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng ( -34, 151 );
        //mMap.addMarker ( new MarkerOptions ().position ( sydney ).title ( "Marker in Sydney" ) );
        //mMap.moveCamera ( CameraUpdateFactory.newLatLng ( sydney ) );
        lm = (LocationManager) this.getSystemService ( Context.LOCATION_SERVICE );

        ll = new LocationListener () {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                l = new LatLng ( location.getLatitude (), location.getLongitude () );
                mMap.addMarker ( new MarkerOptions ().position ( l ).title ( "My location" ) );
                mMap.moveCamera ( CameraUpdateFactory.newLatLng ( l ) );


                String latitude = Double.toString ( location.getLatitude () );
                String longitude = Double.toString ( location.getLongitude () );
                HashMap<String, Object> result = new HashMap<> ();
                result.put ( "Latitude", latitude );
                result.put ( "Longitude", longitude );
                drf.child ( u.getUid () ).updateChildren ( result ).addOnSuccessListener ( new OnSuccessListener<Void> () {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText ( Userlocation.this, "success", Toast.LENGTH_SHORT ).show ();
                    }
                } ).addOnFailureListener ( new OnFailureListener () {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText ( Userlocation.this, "" + e.getMessage (), Toast.LENGTH_SHORT ).show ();
                    }
                } );
            }
        };
        askLocationPermission ();
    }

    private void askLocationPermission() {
        Dexter.withActivity ( Userlocation.this ).withPermission ( Manifest.permission.ACCESS_FINE_LOCATION ).withListener ( new PermissionListener () {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                if (ActivityCompat.checkSelfPermission ( getBaseContext (), Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission ( getBaseContext (), Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                lm.requestLocationUpdates ( LocationManager.GPS_PROVIDER, 0, 0, ll );

                Location lastLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                l = new LatLng ( lastLocation.getLatitude (), lastLocation.getLongitude () );
                mMap.addMarker ( new MarkerOptions ().position ( l ).title ( "My location" ) );
                mMap.moveCamera ( CameraUpdateFactory.newLatLng ( l ) );


                String latitude = Double.toString ( lastLocation.getLatitude () );
                String longitude = Double.toString ( lastLocation.getLongitude () );
                HashMap<String, Object> result = new HashMap<> ();
                result.put ( "Latitude", latitude );
                result.put ( "Longitude", longitude );
                drf.child ( u.getUid () ).updateChildren ( result ).addOnSuccessListener ( new OnSuccessListener<Void> () {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText ( Userlocation.this, "success", Toast.LENGTH_SHORT ).show ();
                    }
                } ).addOnFailureListener ( new OnFailureListener () {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText ( Userlocation.this, "" + e.getMessage (), Toast.LENGTH_SHORT ).show ();
                    }
                } );
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest ();
            }
        } ).check ();
    }

    public void getMapdetail(View v){

    }
}