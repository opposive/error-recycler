package com.thiha.hswagata.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.thiha.hswagata.R;
import com.thiha.hswagata.ui.home.rvadapter;
import com.thiha.hswagata.ui.home.rvmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class StoreView extends AppCompatActivity {

    CircularImageView sl;
    TextView n,c,p,o;

    FirebaseUser u = FirebaseAuth.getInstance ().getCurrentUser ();
    DatabaseReference drf,dbref;
    FirebaseDatabase db;
    StorageReference ssss;
    FirebaseAuth fa;

    List<String> Spiner;

    List<rvmodel> cl;
    rvadapter adapter;
    RecyclerView rev;
    ProgressBar pb;
    String StoreUID,Username;
    ImageButton s,ss,sss,ssss1,sssss,cls;
    ScrollView sv;
    Button r;
    private String Uname;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_store_view );


        String StoreType =  getIntent ().getStringExtra       ( "Sttype" ) ;
        String StoreName = getIntent ().getStringExtra        ( "Stname" ) ;
        String StoreLogo =  getIntent ().getStringExtra      ( "Stlogo" );
        String StorePhone =  getIntent ().getStringExtra      ( "StPhone" ) ;
        StoreUID =  getIntent ().getStringExtra    ( "stuid" ) ;
        String OwnerName =  getIntent ().getStringExtra    ( "Onname" ) ;
        String OwnerProfile =  getIntent ().getStringExtra    ( "Onprofile" ) ;



        sl =findViewById ( R.id.slo );
        n = findViewById ( R.id.sn );
        /*c =findViewById ( R.id.cat );
        p =findViewById ( R.id.sph );
        o = findViewById ( R.id.ownn);*/
        s = findViewById ( R.id.s1 );
        ss= findViewById ( R.id.s2 );
        sss= findViewById ( R.id.s3 );
        ssss1 = findViewById ( R.id.s4 );
        sssss = findViewById ( R.id.s5 );
        cls = findViewById ( R.id.clo );
        sv =findViewById ( R.id.hid );
        r =findViewById ( R.id.rate );
        
        rev = findViewById ( R.id.prd );
        pb = findViewById ( R.id.pb );

        fa = FirebaseAuth.getInstance ();
        u = fa.getCurrentUser ();
        db = FirebaseDatabase.getInstance ();
        dbref = db.getReference ("Marchant");
        drf = db.getReference ("Users");
        ssss = FirebaseStorage.getInstance ().getReference ();

        if (u != null) {
            Query query = drf.orderByChild ( "UID" ).equalTo ( u.getUid () );
            query.addValueEventListener ( new ValueEventListener () {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren ())
                        Username = "" + ds.child ( "Name" ).getValue ();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            } );
        }

        dbref.child ( StoreUID ).child ( "raters" ).addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child ( Username ).exists ()){
                    sv.setVisibility ( View.GONE );
                    r.setVisibility ( View.GONE );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );


        r.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                sv.setVisibility ( View.VISIBLE );
                r.setVisibility ( View.GONE );
            }
        } );

        cls.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                if (!s.isEnabled ()){

                    sv.setVisibility ( View.GONE );
                    r.setVisibility ( View.GONE );

                }else {
                    sv.setVisibility ( View.GONE );
                    r.setVisibility ( View.VISIBLE );
                }

            }
        } );

        try {
            Picasso.get ().load ( StoreLogo ).placeholder ( R.drawable.ic_itemphoto ).into ( sl );
        } catch (Exception e) {
            Picasso.get ().load ( R.drawable.ic_itemphoto ).into ( sl );
        }

        n.setText ( StoreName );
        /*c.setText ( "StoreType:\t"+StoreType );
        p.setText ( "StorePhone:\t"+StorePhone );
        o.setText ( "OwnerName:\t"+OwnerName );*/

        pb.setVisibility ( View.VISIBLE );
        LinearLayoutManager llm = new LinearLayoutManager(StoreView.this);
        llm.setStackFromEnd ( true );
        llm.setReverseLayout ( true );
        rev.setLayoutManager(llm);
        cl = new ArrayList<> ();

        new android.os.Handler (  ).postDelayed (
                new Runnable () {
                    @Override
                    public void run() {
                        loadItem ();

                    }
                },10000);

        s.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String s = "star 1 rater";
                rate ( s );
            }
        } );

        ss.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                String s = "star 2 rater";
                rate ( s );


            }
        } );

        sss.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String s = "star 3 rater";
                rate ( s );

            }
        } );

        ssss1.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String s = "star 4 rater";
                rate ( s );

            }
        } );

        sssss.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String s = "star 5 rater";
                rate ( s );

            }
        } );
    }

    private void loadItem() {
        DatabaseReference dvr = FirebaseDatabase.getInstance ().getReference ("Marchant").child ( StoreUID ).child ( "Items" );
        dvr.addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cl.clear ();
                for (DataSnapshot ds:snapshot.getChildren ()){
                    rvmodel m = ds.getValue (rvmodel.class);
                    cl.add ( m );
                    adapter = new rvadapter ( StoreView.this,cl );
                    pb.setVisibility ( View.GONE );
                    rev.setAdapter ( adapter );

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );
    }

    private void rate(String nostar){
        final HashMap<String,Object> result = new HashMap<> (  );
        result.put ( u.getUid (),Username );
        dbref.child ( StoreUID ).child ( nostar ).updateChildren ( result ).addOnSuccessListener ( new OnSuccessListener<Void> () {
            @Override
            public void onSuccess(Void aVoid) {
                switch ( nostar ){
                    case "star 1 rater":

                        s.setImageResource ( R.drawable.ic_r );
                        s.setEnabled ( false );
                        ss.setEnabled ( false );
                        sss.setEnabled ( false );
                        ssss1.setEnabled ( false );
                        sssss.setEnabled ( false );
                        break;
                    case "star 2 rater":

                        s.setImageResource ( R.drawable.ic_r );
                        ss.setImageResource ( R.drawable.ic_r );
                        s.setEnabled ( false );
                        ss.setEnabled ( false );
                        sss.setEnabled ( false );
                        ssss1.setEnabled ( false );
                        sssss.setEnabled ( false );
                        break;
                    case "star 3 rater":
                        s.setImageResource ( R.drawable.ic_r );
                        sss.setImageResource ( R.drawable.ic_r );
                        ss.setImageResource ( R.drawable.ic_r );
                        s.setEnabled ( false );
                        ss.setEnabled ( false );
                        sss.setEnabled ( false );
                        ssss1.setEnabled ( false );
                        sssss.setEnabled ( false );
                        break;
                    case "star 4 rater":
                        s.setImageResource ( R.drawable.ic_r );
                        sss.setImageResource ( R.drawable.ic_r );
                        ss.setImageResource ( R.drawable.ic_r );
                        ssss1.setImageResource ( R.drawable.ic_r );
                        s.setEnabled ( false );
                        ss.setEnabled ( false );
                        sss.setEnabled ( false );
                        ssss1.setEnabled ( false );
                        sssss.setEnabled ( false );
                        break;
                    case "star 5 rater":
                        s.setImageResource ( R.drawable.ic_r );
                        sss.setImageResource ( R.drawable.ic_r );
                        ss.setImageResource ( R.drawable.ic_r );
                        sssss.setImageResource ( R.drawable.ic_r );
                        ssss1.setImageResource ( R.drawable.ic_r );
                        s.setEnabled ( false );
                        ss.setEnabled ( false );
                        sss.setEnabled ( false );
                        ssss1.setEnabled ( false );
                        sssss.setEnabled ( false );

                        break;

                }
                dbref.child ( StoreUID ).child ( "raters" ).child ( Username ).setValue ( true ).addOnSuccessListener ( new OnSuccessListener<Void> () {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText ( StoreView.this, "You rated this Store", Toast.LENGTH_SHORT ).show ();
                    }
                } ).addOnFailureListener ( new OnFailureListener () {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText ( StoreView.this, "Failed!"+e.getMessage (), Toast.LENGTH_SHORT ).show ();
                    }
                } );
                Toast.makeText ( StoreView.this, "You rated this Store", Toast.LENGTH_SHORT ).show ();
            }
        } ).addOnFailureListener ( new OnFailureListener () {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText ( StoreView.this, "Failed!"+e.getMessage (), Toast.LENGTH_SHORT ).show ();
            }
        } );
    }

    @Override
    protected void onStart() {
        super.onStart ();
    }
}