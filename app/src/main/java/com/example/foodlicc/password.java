package com.example.foodlicc;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodlicc.Common.Common;
import com.example.foodlicc.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        final EditText pss=(EditText)findViewById(R.id.passwordlog);
        Button next=(Button)findViewById(R.id.nxt);



        final FirebaseDatabase db=FirebaseDatabase.getInstance();

        final DatabaseReference table_user=db.getReference("user");




        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String number = getIntent().getStringExtra("number");
                            User u= dataSnapshot.child(number).getValue(User.class);
                            if(u.getPassword().equals(pss.getText().toString())){


                                Toast.makeText(password.this, "Connected !!", Toast.LENGTH_SHORT).show();
                                Intent intent= new Intent(password.this,Home.class);
                                u.setPhone(number);
                               
                                Common.current_user= u;
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_in_left);
                                finish();
                            }
                            else {
                                Toast.makeText(password.this, "Incorrect Password or Email!!", Toast.LENGTH_SHORT).show();



                            }



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }
}
