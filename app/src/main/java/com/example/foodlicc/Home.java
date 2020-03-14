package com.example.foodlicc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodlicc.Common.Common;
import com.example.foodlicc.model.Restaurant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;


public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView name,mail;
    Button goToCart;
    ArrayList<Restaurant> list;
    MyAdapter adapter;
    RelativeLayout rel;
    private boolean isSinglePressed;
    SwipeRefreshLayout swipeRefreshLayout;



    RecyclerView r;
    LinearLayoutManager lay;


    @Override
    protected void onPostResume() {
        super.onPostResume();
        LoadHome();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        FirebaseMessaging.getInstance().subscribeToTopic("NEWS");



        r=(RecyclerView)findViewById(R.id.rec);
        goToCart=(Button)findViewById(R.id.gotocart);
        rel=(RelativeLayout)findViewById((R.id.relativeLayout)) ;
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        r.setLayoutManager(linearLayoutManager);
        r.setHasFixedSize(true);
        LoadHome();

        swipeRefreshLayout = findViewById(R.id.swipeHome);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                LoadHome();

                swipeRefreshLayout.setRefreshing(false);
            }
        });















        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(Home.this,Cart.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_in_left);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        View headerView=navigationView.getHeaderView(0);
        name=(TextView) headerView.findViewById(R.id.username);
        mail=(TextView) headerView.findViewById(R.id.em);
        ImageView imageView=(ImageView)headerView.findViewById(R.id.imageView);


        name.setText(Common.current_user.getFamilly_name()+" "+Common.current_user.getName());
        mail.setText(Common.current_user.getEmail());
        Picasso.get().load(Common.current_user.getPic()).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this,EditProfile.class);
                startActivity(intent);
            }
        });












    }

    private void LoadHome(){

        final DatabaseReference table_restaurant;
        table_restaurant= FirebaseDatabase.getInstance().getReference().child("Restaurant");
        table_restaurant.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<Restaurant>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Restaurant p = dataSnapshot1.getValue(Restaurant.class);
                    list.add(p);
                }
                adapter = new MyAdapter(Home.this,list);
                r.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Home.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });


        }





    @Override
    public void finish() {
        super.finish();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        if(isSinglePressed)
        {
            super.onBackPressed();
        }
        else
        {
            isSinglePressed = true;
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isSinglePressed = false;
                }
            },2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent(Home.this,MainActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_restaurant) {

            // Handle the camera action
        } else if (id == R.id.nav_cart) {

        }  else if (id == R.id.nav_orders) {
            Intent intent = new Intent(Home.this, OrderStatusActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_sign_out) {
            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent(Home.this,MainActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
