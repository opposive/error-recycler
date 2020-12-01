package com.thiha.hswagata.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.thiha.hswagata.ItemView;
import com.thiha.hswagata.R;

import java.util.List;

public class rvadapter extends RecyclerView.Adapter<rvadapter.holder> {

    Context cc;
    List<rvmodel> rm;

    public rvadapter(Context cc, List<rvmodel> rm) {
        this.cc =cc;
        this.rm = rm;
    }



    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from ( cc ).inflate ( R.layout.rvlayout,parent,false );
        return new holder ( v );

    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

        final String Name = rm.get ( position ).getItemName ();
        final String Type = rm.get ( position ).getCategory ();
        final int Price = rm.get ( position ).getPrice ();
        final String Color = rm.get ( position ).getCOlor ();
        final String Picture = rm.get ( position ).getItemimage ();
        final String Description = rm.get ( position ).getDescription ();
        final String postid = rm.get(position).getPostid ();
        final String Store = rm.get ( position ).getStoreName ();
        final String StoreUID = rm.get ( position ).getStoreUid ();
        final String StoreLogo = rm.get ( position ).getStorelogo ();
        final String Owner = rm.get ( position ).getOwner ();
        final String StorePh = rm.get ( position ).getStoreph ();
        final int Itemleft = rm.get ( position ).getLeftitem ();
        final String Usagetime = rm.get ( position ).getUsageTime ();

        holder.a.setText ( Name );
        holder.b.setText ( Color+"များရရှိနိုင်ပါသည်။" );
        holder.c.setText ( Type );
        holder.d.setText ( Price+"-MMK" );
        holder.e.setText ( "လက်ကျန်:"+Itemleft+"ခု" );
        holder.f.setText ( "အသုံးပြုနိုင်သောအချိန်:"+Usagetime );
        try {
            Picasso.get ().load ( Picture ).placeholder ( R.drawable.ic_itemphoto ).into ( holder.mip );
        } catch (Exception e) {
            Picasso.get ().load ( R.drawable.ic_itemphoto ).into ( holder.mip );
        }
        holder.ll.setOnLongClickListener ( new View.OnLongClickListener () {
            @Override
            public boolean onLongClick(View v) {
                Intent iv = new Intent ( cc, ItemView.class );
                iv.putExtra ( "Name",Name );
                iv.putExtra ( "Type",Type );
                iv.putExtra ( "Price",Price );
                iv.putExtra ( "Color",Color );
                iv.putExtra ( "Picture",Picture );
                iv.putExtra ( "Description",Description );
                iv.putExtra ( "postid",postid );
                iv.putExtra ( "StoreName",Store );
                iv.putExtra ( "logo",StoreLogo );
                iv.putExtra ( "StoreUID",StoreUID );
                iv.putExtra ( "StoreOwner",Owner );
                iv.putExtra ( "StorePh",StorePh );
                iv.putExtra ( "il",Itemleft );
                iv.putExtra ( "ut",Usagetime );
                cc.startActivity(iv);
                return false;
            }
        } );

        holder.ll.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Toast.makeText ( cc, "to contact"+StorePh, Toast.LENGTH_SHORT ).show ();
            }
        } );
    }

    @Override
    public int getItemCount() {
        return rm.size ();
    }

    static class holder extends RecyclerView.ViewHolder{

        TextView a;
        TextView b;
        TextView c;
        TextView d,e,f;
        ImageView mip;
        LinearLayout ll;

        public holder(@NonNull View itemView) {
            super ( itemView );

            a = itemView.findViewById( R.id.it);
            b = itemView.findViewById ( R.id.ict );
            c = itemView.findViewById ( R.id.itt );
            d = itemView.findViewById ( R.id.ipt );
            e = itemView.findViewById(R.id.lfi);
            f = itemView.findViewById(R.id.ilt);

            mip = itemView.findViewById ( R.id.ii );
            ll = itemView.findViewById ( R.id.line );

        }
    }
}
