package com.thiha.hswagata;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.thiha.hswagata.Messaging.ChatAdapter;
import com.thiha.hswagata.Messaging.Chatmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    EditText msg;
    ImageView m;
    TextView mn;
    ImageButton s;

    List<Chatmodel> chatm;
    ChatAdapter chata;
    RecyclerView mv;

    FirebaseAuth fa;
    FirebaseUser u;
    DatabaseReference drf,dbref,ch;
    FirebaseDatabase db;

    String suid,what,SMS,Name,Home,Phone,Photo,merchantphoto,merchatName,mes,Myuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_message );

        fa= FirebaseAuth.getInstance ();
        u = fa.getCurrentUser ();

        db = FirebaseDatabase.getInstance ();
        drf = db.getReference ("Users");
        dbref = db.getReference ("Marchant");
        ch = db.getReference ("Chats");

        suid = getIntent ().getStringExtra ( "StoreUID" );
        /*merchatName = getIntent ().getStringExtra ( "SName" );
        merchantphoto = getIntent ().getStringExtra ( "SLogo" );
        what = getIntent ().getStringExtra ( "what" );
        if (what=="Bought"){
            SMS = getIntent ().getStringExtra ( "SMS" );
            msg.setText ( SMS );
        }*/

        Myuid = u.getUid ();

        //Toast.makeText ( MessageActivity.this, ""+merchatName, Toast.LENGTH_SHORT ).show ();



        //components
        msg = findViewById ( R.id.Msg );
        m = findViewById(R.id.merchant);
        mn = findViewById ( R.id.mername );
        s = findViewById ( R.id.sent );
        mv = findViewById ( R.id.mesv );

        //firebase
        fa= FirebaseAuth.getInstance ();
        u = fa.getCurrentUser ();
        db = FirebaseDatabase.getInstance ();
        drf = db.getReference ("Users");
        dbref = db.getReference ("Marchant");

        holy ();

        LinearLayoutManager llo = new LinearLayoutManager (this);
        llo.setStackFromEnd ( true );
        llo.setReverseLayout ( true );
        mv.setLayoutManager(llo);
        chatm = new ArrayList<> ();


        ch.addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatm.clear ();
                for(DataSnapshot ds:snapshot.getChildren ()){
                    Chatmodel cm = ds.getValue ( Chatmodel.class);
                        if (cm.getReciever ().equals ( Myuid ) && cm.getSender ().equals ( suid )
                                || cm.getReciever ().equals ( suid ) && cm.getSender ().equals ( Myuid )){

                            chatm.add ( cm );
                            //Toast.makeText ( MessageActivity.this, "true"+chatm, Toast.LENGTH_SHORT ).show ();
                        }
                }
                chata = new ChatAdapter ( MessageActivity.this,chatm,merchantphoto );
                mv.setAdapter ( chata );
                if (mv.getAdapter ()==null){
                    Toast.makeText ( MessageActivity.this, "False", Toast.LENGTH_SHORT ).show ();
                }else if(mv.getAdapter ()==chata){
                    Toast.makeText ( MessageActivity.this, "True", Toast.LENGTH_SHORT ).show ();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText ( MessageActivity.this, ""+error.getMessage (), Toast.LENGTH_SHORT ).show ();
            }
        } );

        s.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                mes = msg.getText ().toString ().trim ();
                if (TextUtils.isEmpty ( mes )){
                    Toast.makeText ( MessageActivity.this, "Please type a message", Toast.LENGTH_SHORT ).show ();
                }else{
                    sendMessage(mes);
                }
            }
        } );

    }



    private void sendMessage(String mes) {

        String timestamp = String.valueOf ( System.currentTimeMillis () );
        DatabaseReference d = FirebaseDatabase.getInstance().getReference( "Chats" );
        HashMap<String, Object> h = new HashMap<>();
        h.put ( "sender",u.getUid () );
        h.put ( "message",mes );
        h.put ( "reciever",suid );
        h.put ( "timestamp",timestamp );
        d.push ().setValue ( h );
        msg.setText ( "" );
        msg.setHint ( "Text..." );

    }

    private void holy(){
        if (u!=null) {
            Query q = drf.orderByChild ( "UID" ).equalTo ( u.getUid () );
            q.addValueEventListener ( new ValueEventListener () {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren ()) {
                        Photo = "" + ds.child ( "profile" ).getValue ();
                        Name = "" + ds.child ( "Name:" ).getValue ();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            } );

            Query qq = dbref.orderByChild ( "UID" ).equalTo ( suid );
            qq.addValueEventListener ( new ValueEventListener () {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren ()) {
                        merchantphoto = "" + ds.child ( "Ownerprofile" ).getValue ();
                        merchatName = "" + ds.child ( "StoreName" ).getValue ();
                        mn.setText ( merchatName );
                        try {
                            Picasso.get ().load ( merchantphoto ).placeholder ( R.drawable.ic_itemphoto ).into ( m );
                        } catch (Exception e) {
                            Picasso.get ().load ( R.drawable.ic_itemphoto ).into ( m );
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            } );
        }
    }


}

