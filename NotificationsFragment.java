package com.thiha.hswagata.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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
import com.thiha.hswagata.UserInfo.Userinfo;

import org.w3c.dom.Text;

import java.util.UUID;

public class NotificationsFragment extends Fragment {

    ImageView i,p;
    TextView n,e,un;
    Button b;

    FirebaseAuth fa;
    FirebaseUser u;
    DatabaseReference drf;
    FirebaseDatabase db;
    ProgressBar Prob;


    String Name,Email,Profile;
    String UUID;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate ( R.layout.fragment_notifications, container, false );

        p = root.findViewById ( R.id.prof );
        un = root.findViewById ( R.id.unam );
        fa= FirebaseAuth.getInstance ();
        u = fa.getCurrentUser ();

        db = FirebaseDatabase.getInstance ();
        drf = db.getReference ("Users");
        //b = root.findViewById ( R.id.ui );
        Prob = root.findViewById ( R.id.pbpb );
        UUID = u.getUid ().toString ().trim ();

        if (u != null) {
            Query query = drf.orderByChild ( "Email" ).equalTo ( u.getEmail () );

            query.addValueEventListener ( new ValueEventListener () {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren ()) {
                        String name = "" + ds.child ( "Name" ).getValue ();
                        String Email = "" + ds.child ( "Email" ).getValue ();
                        String Photo = "" + ds.child ( "profile" ).getValue ();
                        try {
                            Picasso.get ().load ( Photo ).placeholder ( R.drawable.ic_ab ).into ( p );
                            un.setText ( name );
                            Prob.setVisibility ( View.GONE );
                        } catch (Exception e) {
                            Picasso.get ().load ( R.drawable.ic_ab ).into ( p );
                            un.setText ( name );
                            Toast.makeText ( getActivity (), "Error loading Profile or you didn't uploaded profile", Toast.LENGTH_LONG ).show ();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            } );
        }


        un.setText ( Name );
        try {
            Picasso.get ().load ( Profile ).placeholder ( R.drawable.uuup ).into ( p );
        } catch (Exception e) {
            Picasso.get ().load ( R.drawable.uuup ).into ( p );
        }

        /*
        b.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity ( new Intent ( getActivity (),Userinfo.class ) );
            }
        } );*/


        /*i = root.findViewById ( R.id.uprofile );
        n = root.findViewById ( R.id.uname );
        e = root.findViewById ( R.id.uemail );

        if (u != null) {
            Query query = drf.orderByChild ( "Email" ).equalTo ( u.getEmail () );

            query.addValueEventListener ( new ValueEventListener () {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren ()) {
                        Name = "" +ds.child ( "Name" ).getValue ();
                        Email = ""+ds.child ( "Email" ).getValue ();
                        Profile = ""+ds.child ( "profile" ).getValue ();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            } );
        }
        n.setText ( Name );
        e.setText ( Email );
        try {
            Picasso.get ().load ( Profile ).placeholder ( R.drawable.ic_itemphoto ).into ( i );
        }catch (Exception e){
            Picasso.get ().load ( R.drawable.ic_itemphoto ).into ( i );
        }
*/
        return root;
    }
}