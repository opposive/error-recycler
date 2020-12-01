package com.thiha.hswagata;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.thiha.hswagata.UserInfo.Firstact;

public class splash extends AppCompatActivity {

    FirebaseAuth fa;
    Animation ta,ba;
    ImageView i,ii;
    private static int SS = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        getWindow ().setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView ( R.layout.activity_splash );

        ta = AnimationUtils.loadAnimation( this,R.anim.top_animation);
        ba = AnimationUtils.loadAnimation( this,R.anim.button_animation);
        i = findViewById(R.id.i1);
        ii = findViewById ( R.id.i2 );

        i.setAnimation ( ta );
        ii.setAnimation ( ba );

        /*new Handler (  ).postDelayed ( new Runnable () {
            @Override
            public void run() {
                checkuserstatus ();

            }
        },SS);*/

    }

    @Override
    protected void onStart() {
        super.onStart ();
        new android.os.Handler (  ).postDelayed (

                new Runnable () {
                    @Override
                    public void run() {
                        fa = FirebaseAuth.getInstance ();
                        FirebaseUser user = fa.getCurrentUser();
                        if(user==null){
                            startActivity ( new Intent ( splash.this, Firstact.class ) );
                            finish ();
                        }
                        else {
                            startActivity ( new Intent ( splash.this, MainActivity.class ) );
                            finish ();
                        }
                    }
                },1000);

    }

    private void checkuserstatus(){
        //current user testing


    }
}