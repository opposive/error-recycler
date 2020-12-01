package com.thiha.hswagata.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.thiha.hswagata.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    FirebaseUser u = FirebaseAuth.getInstance ().getCurrentUser ();
    DatabaseReference drf,dbref;
    FirebaseDatabase db;
    StorageReference ssss;
    FirebaseAuth fa;
    Spinner spp;

    List<String> Spiner;

    List<rvmodel> cl;
    rvadapter adapter;
    RecyclerView rev;
    ProgressBar p;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate ( R.layout.fragment_home, container, false );

        rev = root.findViewById ( R.id.prd );
        p = root.findViewById ( R.id.pppb );
        spp = root.findViewById ( R.id.spin );

        fa = FirebaseAuth.getInstance ();
        u = fa.getCurrentUser ();
        db = FirebaseDatabase.getInstance ();
        drf = db.getReference ("Items");
        dbref = db.getReference ("Category");
        ssss = FirebaseStorage.getInstance ().getReference ();
        Spiner = new ArrayList<>();
        Spiner.add ( "All" );
        dbref.addValueEventListener ( new ValueEventListener () {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1:snapshot.getChildren ()) {
                    String Cateee =  snapshot1.getKey ();

                    Spiner.add ( Cateee );
                }

                ArrayAdapter<String> aadpt = new ArrayAdapter<>( getActivity (),android.R.layout.simple_spinner_item,Spiner);
                aadpt.setDropDownViewResource ( android.R.layout.simple_spinner_item );
                spp.setAdapter ( aadpt );
                spp.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener () {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                        p.setVisibility ( View.VISIBLE );
                        LinearLayoutManager lm = new LinearLayoutManager ( getActivity () );
                        lm.setStackFromEnd ( true );
                        lm.setReverseLayout ( true );
                        rev.setLayoutManager(lm);
                        cl = new ArrayList<> ();

                        if (spp.getSelectedItem ().toString ().equals ( "All" )){

                            new android.os.Handler (  ).postDelayed (
                                    new Runnable () {
                                        @Override
                                        public void run() {
                                            loadItem ();
                                        }
                                    },10000);
                        }
                        else{
                            String Catego = spp.getSelectedItem ().toString ();
                            new android.os.Handler (  ).postDelayed (
                                    new Runnable () {
                                        @Override
                                        public void run() {
                                            lloaditem (Catego);
                                        }
                                    },10000);
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {


                        p.setVisibility ( View.VISIBLE );
                        LinearLayoutManager lm = new LinearLayoutManager ( getActivity () );
                        lm.setStackFromEnd ( true );
                        lm.setReverseLayout ( true );
                        rev.setLayoutManager(lm);
                        cl = new ArrayList<> ();

                        new android.os.Handler (  ).postDelayed (
                                new Runnable () {
                                    @Override
                                    public void run() {
                                        loadItem ();
                                    }
                                },1000);

                    }
                } );
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );
        return root;
    }

    private void loadItem() {
        DatabaseReference dvr = FirebaseDatabase.getInstance ().getReference ("Items");
        dvr.addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cl.clear ();
                for (DataSnapshot ds:snapshot.getChildren ()){
                    rvmodel m = ds.getValue (rvmodel.class);
                    cl.add ( m );
                    adapter = new rvadapter ( getActivity (),cl );
                    p.setVisibility ( View.GONE );
                    rev.setAdapter ( adapter );

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );
    }
    private void lloaditem(String dbr){
        DatabaseReference dv = db.getReference ("Category").child ( dbr ).child ( "Items" );
        dv.addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cl.clear ();
                for (DataSnapshot ds:snapshot.getChildren ()){
                    rvmodel m = ds.getValue (rvmodel.class);
                    cl.add ( m );
                    adapter = new rvadapter ( getActivity (),cl );
                    p.setVisibility ( View.GONE );
                    rev.setAdapter ( adapter );

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );
    }


}