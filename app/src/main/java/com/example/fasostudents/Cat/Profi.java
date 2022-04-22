package com.example.fasostudents.Cat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fasostudents.Constante.Constant;
import com.example.fasostudents.Constante.PassAct;
import com.example.fasostudents.Profil.ModifyPro;
import com.example.fasostudents.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Profi extends AppCompatActivity {

    private TextView usn, usc, usf;
    private DatabaseReference userRef;
    private FirebaseAuth mAuth;
    private String userId;
    private ImageView proImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profi);

        Toolbar toolbar= findViewById(R.id.ptb);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        usn= findViewById(R.id.pun);
        usc= findViewById(R.id.psc);
        usf= findViewById(R.id.pf);
        proImg= findViewById(R.id.id_img);

        mAuth= FirebaseAuth.getInstance();
        userId= mAuth.getCurrentUser().getUid();
        userRef= FirebaseDatabase.getInstance().getReference().child(Constant.KEY_USER).child(userId);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String nam, sc, fil, img;
                    nam= snapshot.child(Constant.KEY_USERNAME).getValue().toString();
                    sc= snapshot.child(Constant.KEY_UNIV).getValue().toString();
                    fil= snapshot.child(Constant.KEY_DOMAINE).getValue().toString();
                    img= snapshot.child(Constant.KEY_PROIMG).getValue().toString();

                    usn.setText(nam);
                    usc.setText(sc);
                    usf.setText(fil);

                    try {
                        Picasso.with(getApplicationContext()).load(img).placeholder(R.drawable.image).into(proImg);
                    }catch (Exception e){}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }});

    }

    public void btnModifyPro(View view) {
        new PassAct(this, ModifyPro.class);
    }
}