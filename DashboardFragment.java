package com.thiha.hswagata.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
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
import com.thiha.hswagata.ui.dashboard.adapternmodel.StoreModel;
import com.thiha.hswagata.ui.dashboard.adapternmodel.Storeadapter;

import java.util.ArrayList;
import java.util.List;

//For map view implements OnMapReadyCallback
public class DashboardFragment extends Fragment  {

    /*GoogleMap gm;
    MapView mv;*/

    RecyclerView store;
    FirebaseUser u = FirebaseAuth.getInstance ().getCurrentUser ();
    DatabaseReference drf,dbref;
    FirebaseDatabase db;
    FirebaseAuth fa;
    List<StoreModel> sl;
    Storeadapter adapter;
    ProgressBar p;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate ( R.layout.fragment_dashboard, container, false );
        fa = FirebaseAuth.getInstance ();
        u = fa.getCurrentUser ();
        db = FirebaseDatabase.getInstance ();
        drf = db.getReference ("Marchant");
        store = root.findViewById(R.id.srv);
        p = root.findViewById(R.id.pb);
        LinearLayoutManager lm = new LinearLayoutManager ( getActivity () );
        lm.setStackFromEnd ( true );
        lm.setReverseLayout ( true );
        store.setLayoutManager(lm);
        sl = new ArrayList<> ();
        p.setVisibility ( View.VISIBLE );
        new android.os.Handler (  ).postDelayed (
                new Runnable () {
                    @Override
                    public void run() {
                        loadItem ();
                    }
                },1000);

        return root;
    }

    private void loadItem() {
        DatabaseReference dvr = FirebaseDatabase.getInstance ().getReference ("Marchant");
        dvr.addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sl.clear ();
                for (DataSnapshot ds:snapshot.getChildren ()){
                    StoreModel m = ds.getValue ( StoreModel.class);
                    sl.add ( m );
                    adapter = new Storeadapter ( getActivity (),sl );
                    store.setAdapter ( adapter );
                    p.setVisibility ( View.GONE );

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText ( getActivity (), ""+error, Toast.LENGTH_SHORT ).show ();

            }
        } );
    }

    /*@Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated ( view, savedInstanceState );
        mv = view.findViewById ( R.id.map );
        if (mv !=null){
            mv.onCreate(null);
            mv.onResume ();
            mv.getMapAsync ( this );
        }
    }*/

    /*@Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize ( getContext () );
        gm = googleMap;
    }*/
}