package com.thiha.hswagata.Useracccreate;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.thiha.hswagata.Loadingclass;
import com.thiha.hswagata.R;
import com.thiha.hswagata.UserInfo.Userinfo;

import java.util.HashMap;
import java.util.Objects;

public class Register extends AppCompatActivity {

    FirebaseAuth mauth;
    Button rg;
    EditText e, p;
    TextView ha;
    Loadingclass lc = new Loadingclass ( Register.this );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_register );

        mauth = FirebaseAuth.getInstance ();

        rg = findViewById(R.id.rgg);
        e = findViewById ( R.id.eer2 );
        p = findViewById(R.id.Pw2);
        ha = findViewById ( R.id.rgtlg );

        rg.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                lc.startloading ();

                String em = e.getText ().toString ();
                String ps = p.getText ().toString ();

                //Validation process
                if(!Patterns.EMAIL_ADDRESS.matcher(em).matches()){

                    //show error and focus on email edittext
                    e.setError("Invalid Email");
                    e.setFocusable(true);
                    lc.enddialog ();
                }
                else if(ps.length()<8){

                    //show error and focus on email edittext
                    p.setError("Password must be at least 8 character");
                    p.setFocusable(true);
                    lc.enddialog ();

                }
                else {
                    //email and password condition true and registering account
                    register (em,ps);
                }



            }
        } );

        ha.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent a = new Intent ( Register.this, Login.class );
                startActivity ( a );
            }
        } );

    }

    public void register(String email, String password){


        mauth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult> () {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            if (Objects.requireNonNull ( Objects.requireNonNull ( task.getResult () ).getAdditionalUserInfo () ).isNewUser ()) {
                                FirebaseUser user = mauth.getCurrentUser ();

                                String UID = Objects.requireNonNull ( user ).getUid ();
                                String Email = user.getEmail ();
                                HashMap<Object, String> hashMap = new HashMap<> ();
                                hashMap.put ( "UID", UID );
                                hashMap.put ( "Email", Email );
                                hashMap.put ( "Name", "" );
                                FirebaseDatabase d = FirebaseDatabase.getInstance ();
                                DatabaseReference df = d.getReference ( "Users" );
                                df.child ( UID ).setValue ( hashMap );
                            }
                            lc.enddialog ();
                            startActivity (new Intent ( Register.this, Userinfo.class ));
                        } else {
                            // If sign in fails, display a message to the user.
                            lc.enddialog ();
                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener () {
            @Override
            public void onFailure(@NonNull Exception e) {
                //dismiss progress dialog and get and show error
                lc.enddialog ();
                Toast.makeText(Register.this, "Registering failed.."+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}