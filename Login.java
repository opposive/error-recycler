package com.thiha.hswagata.Useracccreate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.thiha.hswagata.Loadingclass;
import com.thiha.hswagata.MainActivity;
import com.thiha.hswagata.R;

public class Login extends AppCompatActivity {

    Button log;
    TextView nh;
    EditText a,b;
    Loadingclass lc = new Loadingclass ( Login.this );
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_login );

        log = findViewById(R.id.lgg);
        nh =findViewById ( R.id.lgtrg );
        a = findViewById(R.id.eer);
        b =findViewById(R.id.Pw);
        mAuth = FirebaseAuth.getInstance ();

        log.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                lc.startloading ();
                String e = a.getText().toString().trim();
                String p = b.getText().toString();

                if (!Patterns.EMAIL_ADDRESS.matcher(e).matches()){
                    a.setError("Invalid Email");
                    b.setFocusable(true);
                }
                else{
                    LoginUser(e,p);
                }
            }
        } );

        nh.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                startActivity ( new Intent ( Login.this,Register.class ) );
            }
        } );

    }
    private void LoginUser(String e, String p) {
        mAuth.signInWithEmailAndPassword(e, p)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult> () {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent a = new Intent ( Login.this, MainActivity.class );
                            startActivity ( a );
                            lc.enddialog ();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            lc.enddialog ();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener () {
            @Override
            public void onFailure(@NonNull Exception e) {
                lc.enddialog ();
                Toast.makeText(Login.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();//go previous activity
        return super.onSupportNavigateUp();
    }
}