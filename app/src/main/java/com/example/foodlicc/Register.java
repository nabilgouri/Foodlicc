package com.example.foodlicc;

import android.app.ProgressDialog;
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

public class Register extends AppCompatActivity {

    EditText editLNamee, editFName, editPassword,editEmail;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editFName = (EditText) findViewById(R.id.editfname);
        editEmail=(EditText)findViewById(R.id.editEmail);

        editLNamee = findViewById(R.id.lName);
        editPassword = findViewById(R.id.pssreg);
        btnSignUp = findViewById(R.id.btnSignUp);
        final String number3 = getIntent().getStringExtra("number2");

        //Firebase Init
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = firebaseDatabase.getReference("user");


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(Register.this);
                progressDialog.setMessage("Please Wait...");
                progressDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                            progressDialog.dismiss();
                            User user = new User(editEmail.getText().toString(),editFName.getText().toString(),editLNamee.getText().toString(), editPassword.getText().toString(),number3);
                            table_user.child(number3).setValue(user);
                            user.setPhone(number3);
                            Common.current_user= user;

                            Toast.makeText(Register.this, "User created successfully!", Toast.LENGTH_SHORT).show();
                            finish();
                        Intent intent = new Intent(Register.this, Home.class);

                        startActivity(intent);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
