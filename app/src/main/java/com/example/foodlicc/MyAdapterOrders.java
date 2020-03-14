package com.example.foodlicc;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodlicc.model.Request;

import java.util.ArrayList;

public class MyAdapterOrders extends RecyclerView.Adapter<MyAdapterOrders.MyViewHolder> {

    Context context;
    ArrayList<Request> profiles;

    public MyAdapterOrders(Context c , ArrayList<Request> p)
    {
        context = c;
        profiles = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.order_item,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.id.setText(profiles.get(position).getId());


        holder.status.setText(profiles.get(position).getPhone());
        holder.adresse.setText(profiles.get(position).getAddress());






        holder.parentlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,OrderDetail.class);



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
        TextView id,name,adresse,status;
        ImageView profilePic,coverPic;
        CardView parentlayout;
        Button btn;
        public MyViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.order_id);
            adresse=(TextView)itemView.findViewById(R.id.order_address);
            name=(TextView)itemView.findViewById(R.id.order_status);
            status=(TextView)itemView.findViewById(R.id.order_phone);

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

