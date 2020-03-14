package com.example.foodlicc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.foodlicc.Common.Common;
import com.example.foodlicc.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class EditProfile extends AppCompatActivity {

    ImageView ivimage;
    ImageButton ibgallery;
    Button btncancel;
    Button btnupdate;
    EditText etfname;
    EditText etlname;
    EditText etemail;
    RadioGroup rggender;
    RadioButton rbmale;
    RadioButton rbfemale;

    User tempprof;

    int earlierimage=0;

    String imageurl=null;
    String uuid=null;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ivimage = (ImageView) findViewById(R.id.epivimage);
        ibgallery = (ImageButton) findViewById(R.id.epibgallery);
        btncancel = (Button) findViewById(R.id.epbtncancel);
        btnupdate = (Button) findViewById(R.id.epbtnupdate);
        etfname = (EditText) findViewById(R.id.epetfname);
        etlname = (EditText) findViewById(R.id.epetlname);
        etemail = (EditText) findViewById(R.id.epetemail);
        final EditText etpassword=(EditText)findViewById(R.id.epetpassword);
        final EditText etphone=(EditText)findViewById(R.id.epetphone) ;
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.color.bg);

        etfname.setText(Common.current_user.getFamilly_name());
        etlname.setText(Common.current_user.getName());
        etemail.setText(Common.current_user.getEmail());
        etpassword.setText(Common.current_user.getPassword());
        etphone.setText(Common.current_user.getPhone());

        Picasso.get().load(Common.current_user.getPic()).into(ivimage);
        imageurl=Common.current_user.getPic();
        if(imageurl!=null)
            earlierimage=1;












        storageref = storage.getReferenceFromUrl("gs://foodlic.appspot.com/");

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(),"Update Cancelled",Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(),Home.class);
                finish();
                startActivity(i);

                return;
            }
        });

        DatabaseReference mrefcheckprofile = FirebaseDatabase.getInstance().getReference().child("user");



        ibgallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);
            }
        });


        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(etfname.getText().toString().trim().length()<1)
                {
                    etfname.setError("Please enter the correct First name");
                }
                else if(etlname.getText().toString().trim().length()<1)
                {
                    etlname.setError("Please enter the correct Last name");
                }
                else {



                    final User profile = new User();

                    profile.setFamilly_name(etfname.getText().toString().trim());
                    profile.setName(etlname.getText().toString().trim());
                    profile.setEmail(etemail.getText().toString().trim());
                    profile.setPassword(etpassword.getText().toString());
                    profile.setPhone(etphone.getText().toString());




                    profile.setPic(imageurl);


                    if(imageurl!=null){

                        if(earlierimage==1)
                        {
                            if(Common.current_user.getPic().equals(imageurl))
                            {
                                FirebaseDatabase.getInstance().getReference().child("user").child(profile.getPhone()).setValue(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getApplicationContext(),"Profile Updated successfully",Toast.LENGTH_LONG).show();



                                    }
                                });

                            }
                            else
                            {


                                final StorageReference ImagesRef = storageref.child(profile.getPic());
                                ImagesRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        final StorageReference ImagesRef2 = storageref.child("profileImages/"+ UUID.randomUUID()+".jpg");

                                        ImagesRef2.putFile(Uri.parse(imageurl)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                profile.setPic(ImagesRef2.getPath());


                                                FirebaseDatabase.getInstance().getReference().child("user").child(profile.getPhone()).setValue(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(getApplicationContext(),"Profile Updated successfully",Toast.LENGTH_LONG).show();



                                                    }
                                                });

                                            }
                                        });

                                    }
                                });
                            }
                        }
                        else {


                            final StorageReference ImagesRef2 = storageref.child("Brown/"+ UUID.randomUUID()+".jpg");

                            ImagesRef2.putFile(Uri.parse(imageurl)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    profile.setPic(ImagesRef2.getPath());


                                    FirebaseDatabase.getInstance().getReference().child("user").child(Common.current_user.getPhone()).setValue(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getApplicationContext(),"Profile Updated successfully",Toast.LENGTH_LONG).show();

                                            Intent i = new Intent(getApplicationContext(),Home.class);
                                            finish();
                                            startActivity(i);

                                        }
                                    });

                                }
                            });

                        }



                    }else {

                        FirebaseDatabase.getInstance().getReference().child("user").child(Common.current_user.getPhone()).setValue(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(),"Profile Updated successfully",Toast.LENGTH_LONG).show();

                                Intent i = new Intent(getApplicationContext(),Home.class);
                                finish();
                                startActivity(i);

                            }
                        });
                    }

                }

            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == 100) {
                    // Get the url from data
                    Uri selectedImageUri = data.getData();
                    if (null != selectedImageUri) {
                        // Set the image in ImageView
                        ivimage.setImageURI(selectedImageUri);
                        imageurl = selectedImageUri.toString();

                    }

                }
            }
        }
        catch (Exception e)
        {
            Log.d("test",e.getMessage());
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void onBackPressed()
    {
        Intent i = new Intent(getApplicationContext(),Home.class);
        finish();
        startActivity(i);

        return;
    }

}
