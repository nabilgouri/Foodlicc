package com.example.foodlicc;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodlicc.Common.Common;
import com.example.foodlicc.Database.Database;
import com.example.foodlicc.model.Food;
import com.example.foodlicc.model.Request;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.example.foodlicc.FragAdapter;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Restaurant extends AppCompatActivity  {
    ArrayList<com.example.foodlicc.model.Restaurant>list;
    ArrayList<com.example.foodlicc.model.Food>list2;
    RecyclerView recyclerView;
    FragAdapter adapter;
    ImageView imageView;
    Button goToCart;
     String is;
 String s3="";
  String resid="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        String s=getIntent().getStringExtra("img");
        String s2=getIntent().getStringExtra("img2");
         resid=getIntent().getStringExtra("id");
         is=getIntent().getStringExtra("is");
         s3=getIntent().getStringExtra("nomResto");


        ImageView img1;
        TextView name2;

        img1=(ImageView)findViewById(R.id.rest);
        Picasso.get().load(s).into(img1);
        name2=(TextView)findViewById(R.id.name_resto2);
        goToCart=(Button)findViewById(R.id.gotocart);
        name2.setText(s3);
        recyclerView=(RecyclerView)findViewById(R.id.rec2);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        imageView=(ImageView)findViewById(R.id.propic);
        Picasso.get().load(s2).into(imageView);






       Query q1=FirebaseDatabase.getInstance().getReference("Food").orderByChild("menuid").equalTo(resid);


        q1.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                list2 = new ArrayList<Food>();


                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    Food p = dataSnapshot1.getValue(Food.class);
                    list2.add(p);

                }
                adapter = new FragAdapter(Restaurant.this,list2);
                recyclerView.setAdapter(adapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });



        itemclocked();


        goToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                    Intent intent10=new Intent(Restaurant.this,Cart.class);
                    intent10.putExtra("idresto",resid);
                    intent10.putExtra("restonom",s3);

                    startActivity(intent10);




            }
        });










        //TabLayout tb1=(TabLayout)findViewById(R.id.tabLayout);
        //ViewPager viewPager=(ViewPager)findViewById(R.id.viewpager1);
        //TabItem tabItem=(TabItem)findViewById(R.id.fr1);
        //PageAdapter adapter =new PageAdapter(getSupportFragmentManager());
        //adapter.addFrag(new menuFood(),"Menu");

        //viewPager.setAdapter(adapter);
        //tb1.setupWithViewPager(viewPager);







    }

    @Override
    public void onBackPressed() {
        if(new Database(Restaurant.this).isEmpty()){
            showDialog();

        }
        else super.onBackPressed();

    }

    void itemclocked(){

        if(new Database(Restaurant.this).isEmpty()){
            goToCart.setVisibility(View.VISIBLE);
        }




    }
    void showDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure");
        builder.setMessage("you must clear you cart if you want to navigate in other retaurants ");

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        builder.setPositiveButton("Clear Cart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Database(Restaurant.this).cleanCart();
                Intent intent=new Intent(Restaurant.this,Home.class);

                startActivity(intent);

            }
        }).setNegativeButton("View your cart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(Restaurant.this,Cart.class);
                intent.putExtra("idresto",resid);
                intent.putExtra("restonom",s3);
                startActivity(intent);

                dialog.dismiss();

            }
        });
        builder.show();






    }


}
