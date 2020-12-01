package com.thiha.hswagata.Messaging;

import android.content.Context;
import android.os.Build;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.thiha.hswagata.R;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int msg_type_left = 0;
    private static final int msg_type_right = 1;
    FirebaseAuth fa;
    FirebaseUser u;

    List<Chatmodel> chat;
    Context cc;
    String imageUrl;

    public ChatAdapter(Context cc, List<Chatmodel> chat, String imageUrl) {
        this.chat = chat;
        this.cc = cc;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == msg_type_right){
            View v = LayoutInflater.from ( cc ).inflate ( R.layout.mesright_me,parent,false );
            Toast.makeText ( cc, "trueture", Toast.LENGTH_SHORT ).show ();
            return new holder ( v );
        }else{
            View v = LayoutInflater.from ( cc ).inflate ( R.layout.mesleft_store,parent,false );
            Toast.makeText ( cc, "True", Toast.LENGTH_SHORT ).show ();
            return new hh ( v );
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (getItemViewType (position) == msg_type_left) {
            ((holder) viewHolder).setleft(chat.get(position),imageUrl);

        } else {
            ((hh) viewHolder).setright(chat.get(position));
        }




    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int getItemViewType(int position) {

        fa = FirebaseAuth.getInstance ();
        u = fa.getCurrentUser ();
        if (Objects.equals ( chat.get ( position ).getSender (), u.getUid ().toString ().trim () )){
            Toast.makeText ( cc, "right", Toast.LENGTH_SHORT ).show ();
            return msg_type_right;
        }
        else {
            Toast.makeText ( cc, "left", Toast.LENGTH_SHORT ).show ();
            return msg_type_left;
        }
    }

    public static class holder extends RecyclerView.ViewHolder {

        ImageView profile;
        TextView message,timee;

        public holder(@NonNull View itemView) {
            super ( itemView );

            profile = itemView.findViewById( R.id.prf);
            message =  itemView.findViewById(R.id.mes);
            timee =  itemView.findViewById(R.id.time);

        }

        public void setleft(Chatmodel chatmodel,String imgurl) {
            String mssage = chatmodel.getMessage ();
            String time = chatmodel.getTimestamp ();

            Calendar cal = Calendar.getInstance( Locale.ENGLISH);
            cal.setTimeInMillis ( Long.parseLong ( time ) );
            String datetime = DateFormat.format("dd/mm/yy hh:mm:aa",cal).toString();

            message.setText ( mssage );
            timee.setText ( datetime );

            try {
                Picasso.get ().load ( imgurl ).placeholder ( R.drawable.ic_itemphoto ).into ( profile );
            } catch (Exception e) {
                Picasso.get ().load ( R.drawable.ic_itemphoto ).into ( profile );
            }


        }
    }

    public static class hh extends RecyclerView.ViewHolder {

        TextView messae,tim;

        public hh(@NonNull View itemView) {
            super ( itemView );

            messae =  itemView.findViewById(R.id.mes);
            tim =  itemView.findViewById(R.id.time);

        }

        public void setright(Chatmodel chatmodel) {

            String mssage = chatmodel.getMessage ();
            String time = chatmodel.getTimestamp ();

            Calendar cal = Calendar.getInstance( Locale.ENGLISH);
            cal.setTimeInMillis ( Long.parseLong ( time ) );
            String datetime = DateFormat.format("dd/mm/yy hh:mm:aa",cal).toString();

            messae.setText ( mssage );
            tim.setText ( datetime );

        }
    }
}
