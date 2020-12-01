package com.thiha.hswagata.ui.dashboard.adapternmodel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.thiha.hswagata.R;
import com.thiha.hswagata.ui.dashboard.StoreView;

import org.w3c.dom.Text;

import java.util.List;

public class Storeadapter extends RecyclerView.Adapter<Storeadapter.holder> {

    Context con;
    List<StoreModel> sm;
    String ue;
    String Username;
    FirebaseAuth a;
    FirebaseUser u;
    DatabaseReference dbref,drf;
    FirebaseDatabase db;
    String chg;


    public Storeadapter(Context con, List<StoreModel> sm) {
        this.con = con;
        this.sm = sm;
        a = FirebaseAuth.getInstance ();
        u = a.getCurrentUser ();
        db = FirebaseDatabase.getInstance ();
        dbref = db.getReference ("Marchant");
        drf = db.getReference ("Users");
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vve = LayoutInflater.from ( con ).inflate ( R.layout.storelayout,parent,false );
        return new holder ( vve );
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

        String stn = sm.get ( position ).getStoreName ();
        String stl = sm.get ( position ).getStoreLogo ();
        String stt = sm.get ( position ).getStoreType ();
        String stp = sm.get ( position ).getPhone ();
        String on = sm.get ( position ).getOwner ();
        String op = sm.get ( position ).getOwnerprofile ();
        String stuid = sm.get ( position ).getUID ();

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

        holder.Sn.setText ( stn );
        try {
            Picasso.get ().load ( stl ).placeholder ( R.drawable.ic_itemphoto ).into ( holder.Sl );
        } catch (Exception e) {
            Picasso.get ().load ( R.drawable.ic_itemphoto ).into ( holder.Sl );
        }

        holder.stlo.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                Intent stv = new Intent(con, StoreView.class );
                stv.putExtra ( "Sttype",stt );
                stv.putExtra ( "Stname",stn );
                stv.putExtra ( "Stlogo",stl );
                stv.putExtra ( "StPhone",stp );
                stv.putExtra ( "stuid",stuid );
                stv.putExtra ( "Onname",on );
                stv.putExtra ( "Onprofile",op );
                con.startActivity(stv);

            }
        } );

    }

    @Override
    public int getItemCount() {
        return sm.size ();
    }

    public class holder extends RecyclerView.ViewHolder {

        TextView Sn;
        CircularImageView Sl;
        LinearLayout stlo;

        public holder(@NonNull View itemView) {
            super ( itemView );

            Sn = itemView.findViewById ( R.id.ston );
            Sl = itemView.findViewById ( R.id.si );
            stlo = itemView.findViewById ( R.id.sc );

        }
    }
}
