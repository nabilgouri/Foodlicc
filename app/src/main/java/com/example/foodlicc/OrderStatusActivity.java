package com.example.foodlicc;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.foodlicc.Common.Common;
import com.example.foodlicc.model.Request;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderStatusActivity extends AppCompatActivity {

    ArrayList<Request> list;
    RecyclerView recyclerView;
    MyAdapterOrders adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        recyclerView=(RecyclerView)findViewById(R.id.rec);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        LoadOrders();


    }
    private void LoadOrders(){

        final DatabaseReference table_restaurant;
        Query q1= FirebaseDatabase.getInstance().getReference("Request").orderByChild("phone").equalTo(Common.current_user.getPhone());

        q1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<Request>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Request p = dataSnapshot1.getValue(Request.class);
                    list.add(p);
                }
                adapter = new MyAdapterOrders(OrderStatusActivity.this,list);
                recyclerView.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(OrderStatusActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
