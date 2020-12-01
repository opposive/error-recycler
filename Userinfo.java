package com.thiha.hswagata.UserInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.thiha.hswagata.MainActivity;
import com.thiha.hswagata.R;

import java.util.HashMap;

public class Userinfo extends AppCompatActivity {

    private static final int CRC = 100;
    private static final int SRC = 200;
    private static final int CPC = 300;
    private static final int SPC = 400;

    FirebaseUser u = FirebaseAuth.getInstance ().getCurrentUser ();
    DatabaseReference drf;
    FirebaseDatabase db;
    FirebaseAuth fa;
    StorageReference sr;
    String sp = "Users_Profile/*";

    EditText b,c,d,e;
    Spinner f,Myspin;
    ArrayAdapter madpt,fadpt,adpt;
    String record,rec;
    ImageView a;

    Uri image_uri;

    String hlw = "(?![a-zA-Z])";
    String phreg="^0.*(\\[2-9]|[0-9]{7}$)(?![a-zA-Z])";



    EditText i,j,k;
    Button tc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_userinfo );

        //component
        i  = findViewById ( R.id.nn );
        j  = findViewById ( R.id.ph );
        k  = findViewById ( R.id.wok );
        tc = findViewById(R.id.tcam);

        //fbdb
        fa = FirebaseAuth.getInstance ();
        u = fa.getCurrentUser ();
        db = FirebaseDatabase.getInstance ();
        drf = db.getReference ("Users");

        fa = FirebaseAuth.getInstance ();
        final FirebaseUser user = fa.getCurrentUser ();
        db = FirebaseDatabase.getInstance ();
        drf = db.getReference ("Users");
        sr = FirebaseStorage.getInstance ().getReference ();


        //component et hlwar,bno,stn,yk
        b = findViewById ( R.id.hlwar );
        c = findViewById ( R.id.bno );
        d = findViewById(R.id.stn);
        e = findViewById(R.id.yk);
        Myspin =findViewById (R.id.ts);
        f = findViewById ( R.id.ct );

        //image click
        a = findViewById(R.id.profile);
        a.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                showpmalertdialog ();


                if (user != null) {
                    Query query = drf.orderByChild ( "Email" ).equalTo ( user.getEmail () );

                    query.addValueEventListener ( new ValueEventListener () {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds : snapshot.getChildren ()) {
                                String Photo = "" +ds.child ( "profile" ).getValue ();
                                try {
                                    Picasso.get ().load ( Photo ).placeholder ( R.drawable.ic_camera ).into ( a );
                                } catch (Exception e) {
                                    Picasso.get ().load ( R.drawable.ic_camera ).into ( a );
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    } );
                }
            }
        } );

        //City Dropdownlists
        madpt = new ArrayAdapter<> ( this,R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.city) );
        madpt.setDropDownViewResource ( R.layout.support_simple_spinner_dropdown_item );
        Myspin.setAdapter(madpt);
        Myspin.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){

                    case 0:
                        record="ရန်ကုန်မြို့";
                        adpt = new ArrayAdapter<> ( Userinfo.this,
                                R.layout.support_simple_spinner_dropdown_item,
                                getResources().getStringArray(R.array.ygnTownship) );
                        adpt.setDropDownViewResource ( R.layout.support_simple_spinner_dropdown_item );
                        f.setAdapter(adpt);
                        f.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener () {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                switch (i){

                                    case 0:
                                        rec="အလုံမြို့နယ်";
                                        break;

                                    case 1:
                                        rec="ဗဟန်းမြို့နယ်";
                                        break;

                                    case 2:
                                        rec = "ဗိုလ်တထောင်မြို့နယ်";
                                        break;

                                    case 3:
                                        rec = "ဒဂုံဆိပ်ကမ်းမြို့နယ်";
                                        break;

                                    case 4:
                                        rec = "ဒဂုံမြို့နယ်";
                                        break;

                                    case 5:
                                        rec = "ဒလမြို့နယ်";
                                        break;

                                    case 6:
                                        rec = "ဒေါပုံမြို့နယ်";
                                        break;

                                    case 7:
                                        rec = "လှိုင်မြို့နယ်";
                                        break;

                                    case 8:
                                        rec = "လှိုင်သာယာမြို့နယ်       ";
                                        break;

                                    case 9:
                                        rec = "အင်းစိန်မြို့နယ်         ";
                                        break;

                                    case 10:
                                        rec = "ကမာရွတ်မြို့နယ်          ";
                                        break;

                                    case 11:
                                        rec = "ကျောက်တံတားမြို့နယ်      ";
                                        break;

                                    case 12:
                                        rec = "ကြည်းမြင့်တိုင်မြို့နယ်  ";
                                        break;

                                    case 13:
                                        rec = "လမ်းမတော်မြို့နယ်        ";
                                        break;

                                    case 14:
                                        rec = "လသာမြို့နယ်              ";
                                        break;


                                    case 15:
                                        rec = "မရမ်းကုန်းမြို့နယ်       ";
                                        break;

                                    case 16:
                                        rec = "မင်္ဂလာတောင်ညွန့်မြို့နယ်";
                                        break;

                                    case 17:
                                        rec = "မင်္ဂလာဒုံမြို့နယ်       ";
                                        break;

                                    case 18:
                                        rec = "မြောက်ဒဂုံမြို့နယ်       ";
                                        break;

                                    case 19:
                                        rec = "မြောက်ဥက္ကလာပမြို့နယ်    ";
                                        break;

                                    case 20:
                                        rec = "ပန်းပဲတန်းမြို့နယ်       ";
                                        break;

                                    case 21:
                                        rec = "ပုဇွန်တောင်မြို့နယ်      ";
                                        break;
                                    case 22:
                                        rec = "စမ်းချောင်းမြို့နယ်      ";
                                        break;

                                    case 23:
                                        rec = "ဆိပ်ကမ်းမြို့နယ်         ";
                                        break;

                                    case 24:
                                        rec = "ဆိပ်ကြီးခနောင်တိုမြို့နယ်";
                                        break;

                                    case 25:
                                        rec = "ရွှေပြည်သာမြို့နယ်       ";
                                        break;
                                    case 26:
                                        rec = "တောင်ဒဂုံမြို့နယ်        ";
                                        break;
                                    case 27:
                                        rec = "တောင်ဥက္ကလာပမြို့နယ်     ";
                                        break;
                                    case 28:
                                        rec = "တာမွေမြို့နယ်            ";
                                        break;
                                    case 29:
                                        rec = "သာကေတမြို့နယ်            ";
                                        break;
                                    case 30:
                                        rec = "သင်္ကန်းကျွန်းမြို့နယ်   ";
                                        break;

                                    case 31:
                                        rec = "ရန်ကင်းမြို့နယ်";
                                        break;
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                                Toast.makeText ( Userinfo.this, "Pleaseselect", Toast.LENGTH_SHORT ).show ();
                            }
                        } );
                        break;

                    case 1:
                        record="တောင်ကြီးမြို့";

                        //Township dropdownlists
                        fadpt = new ArrayAdapter<> ( Userinfo.this,R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.tgTownship) );
                        fadpt.setDropDownViewResource ( R.layout.support_simple_spinner_dropdown_item );
                        f.setAdapter(fadpt);
                        f.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener () {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                                Toast.makeText ( Userinfo.this, "Pleaseselect", Toast.LENGTH_SHORT ).show ();
                            }
                        } );

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText ( Userinfo.this, "Pleaseselect", Toast.LENGTH_SHORT ).show ();
            }
        } );






        tc.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                String val = i.getText ().toString ().trim ();
                String ph = j.getText ().toString ().trim ();
                String ork = k.getText ().toString ().trim ();
                String Homi = record+rec+e.getText ().toString ().trim ()+"ရပ်ကွက်"+d.getText ().toString ().trim ()+"လမ်း၊"+"တိုက်နံပါတ်"+c.getText ().toString ().trim ()+b.getText ().toString ().trim ()+"လွှာ";

                if (!j.getText ().toString ().trim ().matches ( phreg ) || j.getText ().toString ().length ()>11){
                    //show error and focus on email edittext
                    j.setError("Invalid Phone number");
                    j.setFocusable(true);
                }
                else{
                    if (e.getText ().toString ()==null){

                        //show error and focus on ရပ်ကွက် edittext
                        e.setError("ဖြည့်စွက်ပါ");
                        e.setFocusable(true);
                    }else{
                        if (d.getText ().toString ()==null){
                            d.setError("ဖြည့်စွက်ပါ");
                            d.setFocusable(true);
                        }
                        else{
                            if (c.getText ().toString ()==null){
                                c.setError("ဖြည့်စွက်ပါ");
                                c.setFocusable(true);
                            }
                            else{
                                String hlwar = b.getText ().toString ().trim ();
                                if (!hlwar.matches ( hlw )){
                                    if (Integer.parseInt (hlwar)>34) {
                                        b.setError ( "မှားယွင်းနေပါသည်" );
                                        b.setFocusable ( true );
                                    } else {
                                        updateuinfo ( val, ph, ork, Homi );
                                    }
                                }
                            }
                        }
                    }
                }

            }
        } );
    }
    private void showpmalertdialog() {

        String[] option = {"Camera","Gallery"};
        AlertDialog.Builder bb = new AlertDialog.Builder ( Userinfo.this );
        bb.setTitle ( "Choose Photo Picking method" );
        bb.setItems ( option, new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0){
                    //from camera
                    if (!checkcameraPermission ()){
                        requestCamerapermission ();
                    }else{
                        pickfromcamera ();
                    }
                }
                else if (i == 1 ){
                    //from Gallery
                    if (!checkGalleryPermission ()){
                        requestGallerypermission ();
                    }
                    else {
                        pickfromGallery ();
                    }
                }
            }
        } );

        bb.create ().show ();
    }

    public void updateuinfo(String value,String phone,String Wok,String Home){
        if (!TextUtils.isEmpty ( value )&&!TextUtils.isEmpty ( phone )&&!TextUtils.isEmpty ( Wok )) {
            HashMap<String, Object> rs = new HashMap<> ();
            rs.put ( "Name", value );
            rs.put ( "PhoneNumber", phone );
            rs.put ( "Work", Wok );
            rs.put ( "Home", Home );

            drf.child ( u.getUid () ).updateChildren ( rs ).addOnSuccessListener ( new OnSuccessListener<Void> () {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText ( Userinfo.this, "Updated", Toast.LENGTH_SHORT ).show ();
                    startActivity ( new Intent ( Userinfo.this, MainActivity.class ) );
                }
            } ).addOnFailureListener ( new OnFailureListener () {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText ( Userinfo.this, "Error Updating....", Toast.LENGTH_SHORT ).show ();
                }
            } );
        }
        else
            Toast.makeText ( Userinfo.this, "You didn't fill all we need", Toast.LENGTH_SHORT ).show ();

    }


    // Function to check and request permission
    private boolean checkcameraPermission() {

        boolean result =  ContextCompat.checkSelfPermission( Userinfo.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        boolean result1 =  ContextCompat.checkSelfPermission( Userinfo.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        return result && result1;

    }
    private void requestCamerapermission(){
        String[] CameraPermission = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions( Userinfo.this, CameraPermission, CRC);
    }

    // Function to check and request permission
    private boolean checkGalleryPermission() {

        boolean result =  ContextCompat.checkSelfPermission( Userinfo.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        return result;
    }
    private void requestGallerypermission(){
        String[] GalleryPermission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions( Userinfo.this, GalleryPermission, SRC);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case CRC:
                // Checking whether user granted the permission or not.
                if (grantResults.length > 0) {
                    boolean CameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean StorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (CameraAccepted && StorageAccepted) {
                        pickfromcamera ();
                    }
                    else {
                        Toast.makeText ( Userinfo.this, "Plz accept permissions", Toast.LENGTH_SHORT ).show ();
                    }
                }
            case SRC:
                if (grantResults.length > 0) {

                    boolean GalleryAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (GalleryAccepted) {
                        pickfromGallery();
                    }
                    else {
                        Toast.makeText ( Userinfo.this, "Plz accept permission", Toast.LENGTH_SHORT ).show ();
                    }
                }

        }

        super.onRequestPermissionsResult ( requestCode, permissions, grantResults );
    }
    private void pickfromGallery() {

        //pick from gallery
        Intent gall = new Intent (Intent.ACTION_PICK);
        gall.setType ( "image/*" );
        startActivityForResult ( gall,SPC );

    }
    private void pickfromcamera() {

        //Intent of picking image from device camera
        ContentValues values = new ContentValues (  );
        values.put ( MediaStore.Images.Media.TITLE,"Temp Pic" );
        values.put ( MediaStore.Images.Media.DESCRIPTION,"Temp Description" );

        //put image uri
        image_uri = Userinfo.this.getContentResolver ().insert ( MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values );

        //intent to start camera
        Intent camera = new Intent ( MediaStore.ACTION_IMAGE_CAPTURE );
        camera.putExtra ( MediaStore.EXTRA_OUTPUT, image_uri );
        startActivityForResult ( camera,CPC );

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK){
            if(requestCode == SPC){
                if (data != null) {
                    image_uri = data.getData ();
                    uploadprofile ( image_uri );
                }
            }else if(requestCode == CPC){
                uploadprofile(image_uri);
            }
        }

        super.onActivityResult ( requestCode, resultCode, data );
    }

    private void uploadprofile(final Uri uri) {
        String filepath = sp+"-Profile-"+u.getUid ();
        StorageReference srr = sr.child ( filepath );
        srr.putFile ( uri ).addOnSuccessListener ( new OnSuccessListener<UploadTask.TaskSnapshot> () {

            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage ().getDownloadUrl ();

                while (!uriTask.isSuccessful ());

                Uri downloadUri = uriTask.getResult ();

                if(uriTask.isSuccessful ()){

                    HashMap<String,Object> result = new HashMap<> (  );
                    result.put ( "profile",downloadUri.toString () );
                    drf.child ( u.getUid() ).updateChildren ( result ).addOnSuccessListener ( new OnSuccessListener<Void> () {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText ( Userinfo.this, "success", Toast.LENGTH_SHORT ).show ();
                        }
                    } ).addOnFailureListener ( new OnFailureListener () {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText ( Userinfo.this, ""+e.getMessage (), Toast.LENGTH_SHORT ).show ();
                        }
                    } );
                }
            }
        } ).addOnFailureListener ( new OnFailureListener () {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText ( Userinfo.this, ""+e.getMessage (), Toast.LENGTH_SHORT ).show ();
            }
        } );
    }

}