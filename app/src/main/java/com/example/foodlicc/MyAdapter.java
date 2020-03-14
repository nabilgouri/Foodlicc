package com.example.foodlicc;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodlicc.model.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<com.example.foodlicc.model.Restaurant> profiles;

    public MyAdapter(Context c , ArrayList<com.example.foodlicc.model.Restaurant> p)
    {
        context = c;
        profiles = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.home_item,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.name.setText(profiles.get(position).getName());
        holder.cuisine.setText(profiles.get(position).getCuisine());
        holder.status.setText(profiles.get(position).getStatut());

        if (profiles.get(position).getStatut().equals("open")){

            holder.status.setBackground(context.getDrawable(R.drawable.capsule2));
        }
        else {holder.status.setBackground(context.getDrawable(R.drawable.capsule));}



        Picasso.get().load(profiles.get(position).getCoverpic()).into(holder.coverPic);
        Picasso.get().load(profiles.get(position).getPropic()).into(holder.profilePic);
        holder.parentlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, com.example.foodlicc.Restaurant.class);

                intent.putExtra("img",profiles.get(position).getCoverpic());
                intent.putExtra("nomResto",profiles.get(position).getName());
                intent.putExtra("id",profiles.get(position).getId());
                intent.putExtra("img2",profiles.get(position).getPropic());


                context.startActivity(intent);
                

            }
        });


    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,emai,cuisine,status;
        ImageView profilePic,coverPic;
        CardView parentlayout;
        Button btn;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_resto);

            coverPic = (ImageView) itemView.findViewById(R.id.res);
            profilePic = (ImageView) itemView.findViewById(R.id.im2);
            cuisine=(TextView)itemView.findViewById(R.id.type_couisine);
            status=(TextView)itemView.findViewById(R.id.statut);

            parentlayout=itemView.findViewById(R.id.card_view);


        }
        public void onClick(final int position)
        {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context, position+" is clicked", Toast.LENGTH_SHORT).show();


                }
            });
        }
    }
}

