package com.example.fasostudents.Posts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.fasostudents.Constante.Constant;
import com.example.fasostudents.Constante.IntAct;
import com.example.fasostudents.Main.MainActivity;
import com.example.fasostudents.R;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class PostImg extends AppCompatActivity {

    private ImageView postImg;
    private TextInputEditText postDes;
    private Button sharePost;
    private RelativeLayout rlbtn;
    private static int galary_pick= 1;
    private Uri imageUri;
    private String date, heure, imageUrl, randomKey, userId, username, userProfil;
    private StorageReference postRef;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_img);

        Toolbar toolbar= findViewById(R.id.cpimtb);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        mAuth= FirebaseAuth.getInstance();
        userId= mAuth.getCurrentUser().getUid();
        userRef= FirebaseDatabase.getInstance().getReference();

        postImg= findViewById(R.id.idposimg);
        sharePost= findViewById(R.id.idpostimg_btn);
        postDes= findViewById(R.id.idpimcm);
        rlbtn= findViewById(R.id.rl_pbtn);

        postImg.setOnClickListener(view -> {
            Intent intent= new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, galary_pick);
        });

        sharePost.setOnClickListener(view -> {
            String desc= postDes.getText().toString();
            sharePost.setVisibility(View.GONE);
            rlbtn.setVisibility(View.VISIBLE);

            if (imageUri== null){
                Toast.makeText(this, "Veuillez choisir une photo pour la publication",
                        Toast.LENGTH_SHORT).show();
            }else{
                DatabaseReference ref= userRef.child(Constant.KEY_USER).child(userId);

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Calendar calendar1= Calendar.getInstance();
                        SimpleDateFormat currentDate= new SimpleDateFormat("dd-MMMM-yyyy");
                        date= currentDate.format(calendar1.getTime());

                        Calendar calendar2= Calendar.getInstance();
                        SimpleDateFormat currentTime= new SimpleDateFormat("HH:mm:ss");
                        heure= currentTime.format(calendar2.getTime());
                        randomKey= date+heure;

                        postRef= FirebaseStorage.getInstance().getReference().child(Constant.KEY_POSTS)
                                .child(imageUri.getLastPathSegment()+randomKey+".jpg");

                        postRef.putFile(imageUri).addOnCompleteListener(task -> {
                            if (task.isSuccessful()){
                                Task<Uri> uriTask= task.getResult().getStorage().getDownloadUrl();
                                while (!uriTask.isSuccessful());
                                Uri uri= uriTask.getResult();
                                imageUrl= uri.toString();

                                if (snapshot.exists()){
                                    username= snapshot.child(Constant.KEY_USERNAME).getValue().toString();
                                    userProfil= snapshot.child(Constant.KEY_PROIMG).getValue().toString();

                                    DatabaseReference reference= userRef.child(Constant.KEY_POSTS).child(userId+randomKey);
                                    HashMap userMap= new HashMap();

                                    userMap.put(Constant.KEY_USERNAME, username);
                                    userMap.put(Constant.KEY_DATE, date);
                                    userMap.put(Constant.KEY_HEURE, heure);
                                    userMap.put(Constant.KEY_POSTDES, desc);
                                    userMap.put(Constant.KEY_PROIMG, userProfil);
                                    userMap.put(Constant.KEY_POSTIMG_URL, imageUrl);
                                    userMap.put(Constant.KEY_ID, userId);

                                    reference.updateChildren(userMap).addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()){

                                           // saveData(desc);

                                            Toast.makeText(PostImg.this, "Publication reussie!",
                                                    Toast.LENGTH_SHORT).show();
                                            rlbtn.setVisibility(View.GONE);
                                            sharePost.setVisibility(View.VISIBLE);
                                            new IntAct(getApplicationContext(), MainActivity.class);

                                        }else {
                                            String message= task1.getException().getMessage();
                                            Toast.makeText(PostImg.this, "Erreur: "+message,
                                                    Toast.LENGTH_SHORT).show();
                                            rlbtn.setVisibility(View.GONE);
                                            sharePost.setVisibility(View.VISIBLE);
                                        }
                                    });
                                }
                            }else {
                                String messege= task.getException().getMessage();
                                Toast.makeText(PostImg.this, "Erreur: "+messege, Toast.LENGTH_SHORT).show();
                                rlbtn.setVisibility(View.GONE);
                                sharePost.setVisibility(View.VISIBLE);
                            }
                        });

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });
            }
        });
    }

    private void saveData(String desc) {
        DatabaseReference reference1= userRef.child(Constant.KEY_USER)
                .child(userId).child(Constant.KEY_MYPOSTS).child(userId+randomKey);

        HashMap userMap= new HashMap();

        userMap.put(Constant.KEY_USERNAME, username);
        userMap.put(Constant.KEY_DATE, date);
        userMap.put(Constant.KEY_HEURE, heure);
        userMap.put(Constant.KEY_POSTDES, desc);
        userMap.put(Constant.KEY_PROIMG, userProfil);
        userMap.put(Constant.KEY_POSTIMG_URL, imageUrl);
        userMap.put(Constant.KEY_ID, userId);

        reference1.updateChildren(userMap).addOnCompleteListener(task2 -> {
            if (task2.isSuccessful()){
                Toast.makeText(PostImg.this, "Publication reussie!",
                        Toast.LENGTH_SHORT).show();
                rlbtn.setVisibility(View.GONE);
                sharePost.setVisibility(View.VISIBLE);
                new IntAct(getApplicationContext(), MainActivity.class);
            }else {
                String message= task2.getException().getMessage();
                Toast.makeText(PostImg.this, "Erreur: "+message,
                        Toast.LENGTH_SHORT).show();
                rlbtn.setVisibility(View.GONE);
                sharePost.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode== galary_pick && resultCode== RESULT_OK && data!= null){
            imageUri= data.getData();
            postImg.setImageURI(imageUri);

        }
    }
}