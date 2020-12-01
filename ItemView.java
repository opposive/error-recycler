package com.thiha.hswagata;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

public class ItemView extends AppCompatActivity {

    ImageView itemim;
    TextView n,t,p,c,d;
    int bid;

    ImageButton st,sst,ssst,sssst,ssssst;
    
    FirebaseAuth fa;
    FirebaseUser u;
    DatabaseReference drf,dbref;
    FirebaseDatabase db;

    final int sms = 1;

    String Name,Home,Phone,Photo;
    String ItemID,StoreUID,ItemType,StoreName,StoreLogo;

    Button add,buy;

    RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_item_view );

        bid =getdata ( "appstartsbid",0 );
        fa= FirebaseAuth.getInstance ();
        u = fa.getCurrentUser ();

        db = FirebaseDatabase.getInstance ();
        drf = db.getReference ("Users");
        dbref = db.getReference ("Marchant");

        n = findViewById ( R.id.Name );
        t =findViewById ( R.id.typ );
        p = findViewById ( R.id.prc );
        c =findViewById ( R.id.Color );
        d = findViewById ( R.id.des );
        itemim = findViewById ( R.id.itp );
        st = findViewById ( R.id.st1 );
        sst= findViewById ( R.id.st2 );
        ssst= findViewById ( R.id.st3 );
        sssst = findViewById ( R.id.st4 );
        ssssst = findViewById ( R.id.st5 );

        add= findViewById ( R.id.atc );
        buy = findViewById ( R.id.by );

        rl = findViewById ( R.id.relativeLayout );
        

        if (u != null) {
            Query query = drf.orderByChild ( "Email" ).equalTo ( u.getEmail () );

            query.addValueEventListener ( new ValueEventListener () {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren ()) {
                        Name = "" +ds.child ( "Name" ).getValue ();
                        Home = "" +ds.child ( "Home" ).getValue ();
                        Phone = "" +ds.child ( "PhoneNumber" ).getValue ();
                        Photo = "" +ds.child ( "profile" ).getValue ();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            } );
        }

        String ItemName =  getIntent ().getStringExtra ( "Name" ) ;
        ItemType = getIntent ().getStringExtra ( "Type" ) ;
        int ItemPrice = getIntent ().getIntExtra ( "Price",0  ) ;
        String ItemColor =  getIntent ().getStringExtra ( "Color" ) ;
        String ItemPicture =  getIntent ().getStringExtra ( "Picture" ) ;
        ItemID =  getIntent ().getStringExtra ( "postid" );
        String ItemDescription =  getIntent ().getStringExtra ( "Description" );
        StoreName = getIntent ().getStringExtra ( "StoreName" );
        StoreLogo = getIntent ().getStringExtra ( "logo" );
        StoreUID =  getIntent ().getStringExtra ( "StoreUID" );
        String StoreOwner = getIntent ().getStringExtra ( "StoreOwner" );
        String StorePh = getIntent ().getStringExtra ( "StorePh" );
        int itmlet = getIntent ().getIntExtra ( "il",0 ) ;
        String Usgtime = getIntent ().getStringExtra ( "ut" );

        Toast.makeText ( this, "This item sale store phone"+StorePh, Toast.LENGTH_SHORT ).show ();

        dbref.child ( StoreUID ).child ( "Items" ).child ( ItemID ).child ( "raters" ).addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child ( Name ).exists ()){
                    rl.setVisibility ( View.GONE );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );

        st.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String s = "star 1 rater";
                rate ( s );
            }
        } );

        sst.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                String s = "star 2 rater";
                rate ( s );


            }
        } );

        ssst.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String s = "star 3 rater";
                rate ( s );

            }
        } );

        sssst.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String s = "star 4 rater";
                rate ( s );

            }
        } );

        ssssst.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String s = "star 5 rater";
                rate ( s );

            }
        } );

        n.setText ( ItemName );
        t.setText ( ItemType );
        p.setText ( String.valueOf (ItemPrice) );
        c.setText ( ItemColor );
        d.setText ( ItemDescription );
        try {
            Picasso.get ().load ( ItemPicture ).placeholder ( R.drawable.ic_itemphoto ).into ( itemim );
        } catch (Exception e) {
            Picasso.get ().load ( R.drawable.ic_itemphoto ).into ( itemim );
        }

        add.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> rs = new HashMap<> ();
                rs.put ( "StoreName", StoreName );
                rs.put ( "ItemName", ItemName );
                rs.put ( "ItemType", ItemType );
                rs.put ( "ItemPrice", ItemPrice );
                rs.put ( "ItemColor", ItemColor );
                rs.put ( "ItemPicture", ItemPicture );
                rs.put ( "ItemDescription", ItemDescription );
                rs.put ( "ItemID", ItemID );
                rs.put ( "StoreLogo", StoreLogo );
                rs.put ( "StoreUID", StoreUID );
                rs.put ( "StoreOwner", StoreOwner );
                rs.put ( "StorePhone",StorePh );

                String itid = ItemID;
                String stid = StoreUID;
                String uid = u.getUid ();

                drf.child ( u.getUid () ).child ( "CastedItems" ).child ( itid ).updateChildren ( rs ).addOnSuccessListener ( new OnSuccessListener<Void> () {
                    @Override
                    public void onSuccess(Void aVoid) {
                        HashMap<String, Object> sg = new HashMap<> (  );
                        sg.put ( "UserName", Name );
                        sg.put ( "UserID", uid );
                        sg.put ( "UserHome", Home );
                        sg.put ( "UserPhone", Phone );
                        sg.put ( "UserPhoto", Photo );
                        db.getReference ("Marchant").child ( Objects.requireNonNull ( stid ) ).child ( "Items" ).child ( itid ).child ( "Casted" ).child ( u.getUid () ).updateChildren ( sg ).addOnSuccessListener ( new OnSuccessListener<Void> () {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText ( ItemView.this, "Successfully added to cast", Toast.LENGTH_SHORT ).show ();
                            }
                        } ).addOnFailureListener ( new OnFailureListener () {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText ( ItemView.this, "Failed", Toast.LENGTH_SHORT ).show ();
                            }
                        } );

                    }
                } ).addOnFailureListener ( new OnFailureListener () {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText ( ItemView.this, "Error Updating....", Toast.LENGTH_SHORT ).show ();
                    }
                } );
            }
        } );

        buy.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder ab = new AlertDialog.Builder( ItemView.this);
                ab.setTitle ( "ဝယ်ယူလိုသော ပစ္စည်းအရေအတွက်ကို ထည့်ပါ" );
                final View customLayout = getLayoutInflater().inflate(R.layout.alrtdialg, null);
                ab.setView(customLayout);
                EditText ITMS = customLayout.findViewById(R.id.ITMS);
                TextView TV = customLayout.findViewById ( R.id.pricesss );
                try {
                    TV.setText ( "Price : "+ItemPrice+"MMK" );
                }catch (Exception e){

                }
                    ITMS.addTextChangedListener ( new TextWatcher () {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            try {
                                TV.setText ( "Price : "+ItemPrice+"MMK" );
                            }catch (Exception e){

                            }
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            try {
                                int Nob = Integer.parseInt ( ITMS.getText ().toString () );
                                int total = Nob * ItemPrice ;
                                TV.setText ( "Price : "+total+"MMK" );
                            }catch (Exception e){
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            try {
                                int Nob = Integer.parseInt ( ITMS.getText ().toString () );
                                int total = Nob * ItemPrice;
                                TV.setText ( "Price : "+total+"MMK" );
                            }catch (Exception e){

                            }
                        }
                    } );

                /*if (ITMS == null){
                    try {
                        TV.setText ( "Price : "+ItemPrice+"MMK" );
                    }catch (Exception e){

                    }

                }
                else {

                    int Nob = Integer.parseInt ( ITMS.getText ().toString () );
                    int total = (Nob * Integer.parseInt ( ItemPrice ));
                    TV.setText ( "Price : "+total+"MMK" );
                }*/

                ab.setPositiveButton ( "Buy", new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            int Nob = Integer.parseInt ( String.valueOf ( ITMS.getText () ) );
                            int total = (Nob *  ItemPrice );

                            if (Nob <= itmlet) {
                                if (Checksmspermission ( Manifest.permission.SEND_SMS)&&Checksmspermission ( Manifest.permission.READ_SMS )) {

                                        HashMap<String, Object> rs = new HashMap<> ();
                                        rs.put ( "StoreName", StoreName );
                                        rs.put ( "ItemName", ItemName );
                                        rs.put ( "ItemType", ItemType );
                                        rs.put ( "ItemPrice", ItemPrice );
                                        rs.put ( "ItemColor", ItemColor );
                                        rs.put ( "ItemPicture", ItemPicture );
                                        rs.put ( "ItemDescription", ItemDescription );
                                        rs.put ( "ItemID", ItemID );
                                        rs.put ( "StoreLogo", StoreLogo );
                                        rs.put ( "StoreUID", StoreUID );
                                        rs.put ( "StoreOwner", StoreOwner );
                                        rs.put ( "StorePhone", StorePh );
                                        rs.put ( "Totalprice", total );
                                        rs.put ( "Boughtitemnumb", Nob );

                                        String itid = ItemID;
                                        String stid = StoreUID;
                                        String uid = u.getUid ();

                                        drf.child ( u.getUid () ).child ( "BoughtItems" ).child ( itid ).updateChildren ( rs ).addOnSuccessListener ( new OnSuccessListener<Void> () {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                HashMap<String, Object> sg = new HashMap<> ();
                                                sg.put ( "UserName", Name );
                                                sg.put ( "UserID", uid );
                                                sg.put ( "Boughtitemnumber", Nob );
                                                sg.put ( "UserHome", Home );
                                                sg.put ( "Totalprice", total );
                                                sg.put ( "UserPhone", Phone );
                                                sg.put ( "UserPhoto", Photo );
                                                sg.put ( "StoreName", StoreName );
                                                sg.put ( "ItemName", ItemName );
                                                sg.put ( "ItemID", ItemID );
                                                sg.put ( "StoreUID", StoreUID );
                                                sg.put ( "ItemType", ItemType );
                                                sg.put ( "ItemPrice", ItemPrice );
                                                sg.put ( "ItemColor", ItemColor );
                                                sg.put ( "ItemPicture", ItemPicture );
                                                sg.put ( "ItemDescription", ItemDescription );
                                                sg.put ( "ItemID", ItemID );
                                                sg.put ( "StoreLogo", StoreLogo );
                                                sg.put ( "StoreOwner", StoreOwner );
                                                sg.put ( "StorePhone", StorePh );
                                                sg.put ( "Leftitem",itmlet-Nob );
                                                sg.put ( "UsageTime",Usgtime );

                                                db.getReference ( "Marchant" ).child ( Objects.requireNonNull ( stid ) ).child ( "Items" ).child ( itid ).child ( "Bought" ).child ( u.getUid ()+bid ).updateChildren ( sg ).addOnSuccessListener ( new OnSuccessListener<Void> () {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        db.getReference ( "Items" ).child ( itid ).child ( "Bought" ).child ( u.getUid ()+bid ).updateChildren ( sg ).addOnSuccessListener ( new OnSuccessListener<Void> () {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                db.getReference ( "Marchant" ).child ( Objects.requireNonNull ( stid ) ).child ( "Items" ).child ( itid ).child ( "Leftitem" ).setValue ( itmlet-Nob ).addOnCompleteListener ( new OnCompleteListener<Void> () {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        db.getReference ( "Items" ).child ( itid ).child ( "Leftitem" ).setValue ( itmlet-Nob ).addOnCompleteListener ( new OnCompleteListener<Void> () {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                db.getReference ( "Category" ).child ( ItemType ).child ( "Items" ).child ( itid ).child ( "Leftitem" ).setValue ( itmlet-Nob ).addOnCompleteListener ( new OnCompleteListener<Void> () {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {

                                                                                                bid++;
                                                                                                savedata ( "appstartsbid",bid );
                                                                                                sendmessage ( StoreUID,Nob,Name );
                                                                                    }
                                                                                } ).addOnFailureListener ( new OnFailureListener () {
                                                                                    @Override
                                                                                    public void onFailure(@NonNull Exception e) {

                                                                                    }
                                                                                } );
                                                                            }
                                                                        } ).addOnFailureListener ( new OnFailureListener () {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {

                                                                            }
                                                                        } );
                                                                    }
                                                                } ).addOnFailureListener ( new OnFailureListener () {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {

                                                                    }
                                                                } );
                                                            }
                                                        } ).addOnFailureListener ( new OnFailureListener () {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {

                                                            }
                                                        } );
                                                    }
                                                } ).addOnFailureListener ( new OnFailureListener () {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                    }
                                                } );
                                            }
                                        } ).addOnFailureListener ( new OnFailureListener () {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                            }
                                        } );
                                } else {
                                    ActivityCompat.requestPermissions ( ItemView.this, new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_SMS}, sms );
                                }
                            } else {
                                dialog.dismiss ();
                                Toast.makeText ( ItemView.this, "ပစ္စည်မလုံလောက်သောကြောင့်ဝယ်ယူ၍မရပါ"+itmlet,Toast.LENGTH_SHORT ).show ();
                            }
                    }
                } );
                ab.setNegativeButton ( "Cancle", new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss ();
                    }
                } );
                ab.show ();
            }
        } );
    }
    public boolean Checksmspermission(String Permission){
        int c = ContextCompat.checkSelfPermission ( ItemView.this,Permission );
        return (c== PackageManager.PERMISSION_GRANTED);
    }

    protected void sendmessage(String SUID, int nob, String name){
        /*try {
            String SMS = "သင်၏ကုန်ပစ္စည်းအား" + name + "မှ" + nob + "ခုမှာယူထားပါသည်။ပစ္စည်းလက်ကျန်ကိုအကြောင်ပြန်ပေးပါ။";
            SmsManager sms = SmsManager.getDefault ();
            //Toast.makeText ( ItemView.this, ""+StorePh, Toast.LENGTH_SHORT ).show ();
            sms.sendTextMessage ( Phone, null, SMS, null, null );
            Toast.makeText ( ItemView.this, "sent sms" + Phone, Toast.LENGTH_SHORT ).show ();
        }catch (Exception e){
            Toast.makeText ( ItemView.this, ""+e.getMessage (), Toast.LENGTH_SHORT ).show ();
        }*/
        String SMS = "သင်၏ကုန်ပစ္စည်းအား" + "ကျွန်ုပ်" + nob + "ခုမှာယူလိုပါသည်။ပစ္စည်းလက်ကျန်ကိုအကြောင်ပြန်ပေးပါ။";
        Intent it = new Intent(ItemView.this, MessageActivity.class);
        it.putExtra ( "SMS",SMS );
        it.putExtra ( "StoreUID",SUID );
        it.putExtra ( "what","Bought" );
        it.putExtra ( "SName",StoreName );
        it.putExtra ( "SLogo",StoreLogo );
        startActivity(it);
    }

    private void rate(String nostar){
        final HashMap<String,Object> result = new HashMap<> (  );
        result.put ( u.getUid (),Name );
        dbref.child ( StoreUID ).child ( "Items" ).child ( ItemID ).child ( nostar ).updateChildren ( result ).addOnSuccessListener ( new OnSuccessListener<Void> () {
            @Override
            public void onSuccess(Void aVoid) {
                switch ( nostar ){
                    case "star 1 rater":

                        st.setImageResource ( R.drawable.ic_r );
                        st.setEnabled ( false );
                        sst.setEnabled ( false );
                        ssst.setEnabled ( false );
                        sssst.setEnabled ( false );
                        ssssst.setEnabled ( false );
                        break;
                    case "star 2 rater":

                        st.setImageResource ( R.drawable.ic_r );
                        sst.setImageResource ( R.drawable.ic_r );
                        st.setEnabled ( false );
                        sst.setEnabled ( false );
                        ssst.setEnabled ( false );
                        sssst.setEnabled ( false );
                        ssssst.setEnabled ( false );
                        break;
                    case "star 3 rater":
                        st.setImageResource ( R.drawable.ic_r );
                        ssst.setImageResource ( R.drawable.ic_r );
                        sst.setImageResource ( R.drawable.ic_r );
                        st.setEnabled ( false );
                        sst.setEnabled ( false );
                        ssst.setEnabled ( false );
                        sssst.setEnabled ( false );
                        ssssst.setEnabled ( false );
                        break;
                    case "star 4 rater":
                        st.setImageResource ( R.drawable.ic_r );
                        ssst.setImageResource ( R.drawable.ic_r );
                        sst.setImageResource ( R.drawable.ic_r );
                        sssst.setImageResource ( R.drawable.ic_r );
                        st.setEnabled ( false );
                        sst.setEnabled ( false );
                        ssst.setEnabled ( false );
                        sssst.setEnabled ( false );
                        ssssst.setEnabled ( false );
                        break;
                    case "star 5 rater":
                        st.setImageResource ( R.drawable.ic_r );
                        ssst.setImageResource ( R.drawable.ic_r );
                        sst.setImageResource ( R.drawable.ic_r );
                        ssssst.setImageResource ( R.drawable.ic_r );
                        sssst.setImageResource ( R.drawable.ic_r );
                        st.setEnabled ( false );
                        sst.setEnabled ( false );
                        ssst.setEnabled ( false );
                        sssst.setEnabled ( false );
                        ssssst.setEnabled ( false );

                        break;

                }
                dbref.child ( StoreUID ).child ( "Items" ).child ( ItemID ).child ( "raters" ).child ( Name ).setValue ( true ).addOnSuccessListener ( new OnSuccessListener<Void> () {
                    @Override
                    public void onSuccess(Void aVoid) {
                        db.getReference ("Items").child ( ItemID ).child ( nostar ).child ( Name ).setValue ( true ).addOnSuccessListener ( new OnSuccessListener<Void> () {
                            @Override
                            public void onSuccess(Void aVoid) {
                                db.getReference ("Category").child ( ItemType ).child ( "Items" ).child ( ItemID ).child ( nostar ).child ( Name ).setValue ( true ).addOnSuccessListener ( new OnSuccessListener<Void> () {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        db.getReference ("Items").child ( ItemID ).child ( "raters" ).child ( Name ).setValue ( true ).addOnSuccessListener ( new OnSuccessListener<Void> () {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                db.getReference ("Category").child ( ItemType ).child ( "Items" ).child ( ItemID ).child ( "raters" ).child ( Name ).setValue ( true ).addOnSuccessListener ( new OnSuccessListener<Void> () {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText ( ItemView.this, "You Have rated as:"+nostar+"successfully", Toast.LENGTH_SHORT ).show ();
                                                    }
                                                } ).addOnFailureListener ( new OnFailureListener () {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText ( ItemView.this, "Failed!"+e.getMessage (), Toast.LENGTH_SHORT ).show ();
                                                    }
                                                } );
                                            }
                                        } ).addOnFailureListener ( new OnFailureListener () {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText ( ItemView.this, "Failed!"+e.getMessage (), Toast.LENGTH_SHORT ).show ();
                                            }
                                        } );
                                    }
                                } ).addOnFailureListener ( new OnFailureListener () {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText ( ItemView.this, "Failed!"+e.getMessage (), Toast.LENGTH_SHORT ).show ();
                                    }
                                } );

                            }
                        } ).addOnFailureListener ( new OnFailureListener () {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText ( ItemView.this, "Failed!"+e.getMessage (), Toast.LENGTH_SHORT ).show ();
                            }
                        } );

                    }
                } ).addOnFailureListener ( new OnFailureListener () {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText ( ItemView.this, "Failed!"+e.getMessage (), Toast.LENGTH_SHORT ).show ();
                    }
                } );
            }
        } ).addOnFailureListener ( new OnFailureListener () {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText ( ItemView.this, "Failed!"+e.getMessage (), Toast.LENGTH_SHORT ).show ();
            }
        } );
    }

    private void savedata(String key,int getval){
        SharedPreferences p = getSharedPreferences ( "prefs",0 );
        SharedPreferences.Editor e = p.edit();
        e.putInt ( key,getval );
        e.apply ();
    }
    private int getdata(String key,int def){
        SharedPreferences p = getSharedPreferences ( "prefs",0 );
        return p.getInt ( key,def );
    }


}