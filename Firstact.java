package com.thiha.hswagata.UserInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.thiha.hswagata.R;
import com.thiha.hswagata.Useracccreate.Login;
import com.thiha.hswagata.Useracccreate.Register;

public class Firstact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_firstact2 );

        Button a,b;
        a = findViewById(R.id.Reg);
        b = findViewById(R.id.log);

        a.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                startActivity ( new Intent ( Firstact.this, Register.class ) );
            }
        } );

        b.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                startActivity ( new Intent ( Firstact.this, Login.class ) );
            }
        } );


    }
}