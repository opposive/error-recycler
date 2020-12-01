package com.thiha.hswagata;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class Loadingclass {

    Activity a;
    AlertDialog ad;

    public Loadingclass(Activity Myactivity) {
        a = Myactivity;
    }

    public void startloading(){
        AlertDialog.Builder b = new AlertDialog.Builder(a);

        LayoutInflater i = a.getLayoutInflater();
        b.setView (i.inflate ( R.layout.customprogressdialog,null ));

        b.setCancelable ( true );
        ad = b.create();
        ad.show ();
    }

    public void enddialog(){
        ad.dismiss ();
    }

}
