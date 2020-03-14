package com.example.foodlicc;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodlicc.Common.Common;
import com.example.foodlicc.Database.Database;
import com.example.foodlicc.model.Order;
import com.example.foodlicc.model.Request;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {
    RecyclerView recyclerView;
    public TextView textViewPrice,nomresto;
    Button buttonOrder;
    CartAdapter cartAdapter;
    List<Order> orders;
    DatabaseReference request;
    Button button;
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
         nomresto=(TextView)findViewById(R.id.restoname) ;

        orders = new ArrayList<>();
        textViewPrice = findViewById(R.id.order_price);
        buttonOrder = findViewById(R.id.btnPlaceOrder);
        recyclerView = findViewById(R.id.recycler_cart);
        button=findViewById(R.id.clear);
         firebaseDatabase = FirebaseDatabase.getInstance();
        request = firebaseDatabase.getReference("Request");
        final String s3=getIntent().getStringExtra("restonom");
        nomresto.setText(s3+" is at your service");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        loadCart();
        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewPrice.getText().toString().equals("0DA")){
                    Toast.makeText(Cart.this, "Cart is empty", Toast.LENGTH_SHORT).show();
                }
                else {showDialog();}





            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(Cart.this).cleanCart();
                loadCart();
                nomresto.setText("");
            }
        });






    }
    private void loadCart() {
        orders = new Database(this).getCarts();
        cartAdapter = new CartAdapter(this, orders);
        int total=0;
        recyclerView.setAdapter(cartAdapter);
        for(Order order: orders)
        {
            total += (Integer.parseInt(order.getPrice()) );
        }
        textViewPrice.setText(Integer.toString(total)+" DA");



    }
    void showDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("One more step!");
        builder.setMessage("Enter your Address: ");
        final EditText editText = new EditText(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        editText.setLayoutParams(layoutParams);
        builder.setView(editText);
        builder.setIcon(R.drawable.ic_shopping_cart_black_24dp);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if ( editText.getText().toString().trim().isEmpty() ) {
                    editText.setError("Enter an adress plz");
                    editText.requestFocus();
                    Toast.makeText(Cart.this, "Enter a valid adress please", Toast.LENGTH_SHORT).show();
                    return;

                }
                else {
                    String id=String.valueOf(System.currentTimeMillis());
                Request req = new Request(Common.current_user.getName()+" "+Common.current_user.getFamilly_name(),
                        Common.current_user.getPhone(),
                        editText.getText().toString(),
                        textViewPrice.getText().toString(),
                        orders,getIntent().getStringExtra("idresto"),id);

                //sending to firebase

                request.child(id).setValue(req);
                new Database(Cart.this).cleanCart();
                Toast.makeText(Cart.this, "Order is placed. Thank You!", Toast.LENGTH_SHORT).show();
                    nomresto.setText("");
                    loadCart();

            }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        builder.show();






    }
}
