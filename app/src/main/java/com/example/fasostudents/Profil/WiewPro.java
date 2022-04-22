package com.example.fasostudents.Profil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fasostudents.Constante.Constant;
import com.example.fasostudents.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class WiewPro extends AppCompatActivity {
    
    private String userId, myId;
    private DatabaseReference userRef, myRef;
    private TextView un, ui, ud, cmp, exp, about, addFriend;
    private ImageView proImg, chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wiew_pro);


        userId= getIntent().getExtras().get("id").toString();
        myId= FirebaseAuth.getInstance().getCurrentUser().getUid();
        userRef= FirebaseDatabase.getInstance().getReference().child(Constant.KEY_USER).child(userId);
        myRef= FirebaseDatabase.getInstance().getReference().child(Constant.KEY_USER).child(myId)
                .child(Constant.KEY_FRIENDS);

        un= findViewById(R.id.wp_un);
        ui= findViewById(R.id.wp_sch);
        ud= findViewById(R.id.wp_dm);
        proImg= findViewById(R.id.pimg);
        cmp= findViewById(R.id.id_comp);
        exp= findViewById(R.id.id_exp);
        about= findViewById(R.id.id_desc);
        addFriend= findViewById(R.id.id_addFr);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    addFriend.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String nam, ins, dom, img, comp, exper, desc;
                    nam= snapshot.child(Constant.KEY_USERNAME).getValue().toString();
                    ins= snapshot.child(Constant.KEY_UNIV).getValue().toString();
                    dom= snapshot.child(Constant.KEY_DOMAINE).getValue().toString();
                    img= snapshot.child(Constant.KEY_PROIMG).getValue().toString();
                    comp= snapshot.child(Constant.KEY_COMPETENCE).getValue().toString();
                    exper= snapshot.child(Constant.KEY_EXPER).getValue().toString();
                    desc= snapshot.child(Constant.KEY_PRO_DESCR).getValue().toString();

                    un.setText(nam);
                    ui.setText(ins);
                    ud.setText(dom);
                    cmp.setText(comp);
                    exp.setText(exper);
                    about.setText(desc);
                    try {
                        Picasso.with(getApplicationContext()).load(img).placeholder(R.drawable.image).into(proImg);
                    }catch (Exception e){ }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addFriend.setOnClickListener(view ->
                userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String nam, ins, dom, img, comp, exper, desc;
                    nam= snapshot.child(Constant.KEY_USERNAME).getValue().toString();
                    ins= snapshot.child(Constant.KEY_UNIV).getValue().toString();
                    dom= snapshot.child(Constant.KEY_DOMAINE).getValue().toString();
                    img= snapshot.child(Constant.KEY_PROIMG).getValue().toString();
                    comp= snapshot.child(Constant.KEY_COMPETENCE).getValue().toString();
                    exper= snapshot.child(Constant.KEY_EXPER).getValue().toString();
                    desc= snapshot.child(Constant.KEY_PRO_DESCR).getValue().toString();

                    HashMap userMap= new HashMap();
                    userMap.put(Constant.KEY_USERNAME, nam);
                    userMap.put(Constant.KEY_UNIV, ins);
                    userMap.put(Constant.KEY_DOMAINE, dom);
                    userMap.put(Constant.KEY_PROIMG, img);
                    userMap.put(Constant.KEY_COMPETENCE, comp);
                    userMap.put(Constant.KEY_EXPER, exper);
                    userMap.put(Constant.KEY_PRO_DESCR, desc);

                    myRef.child(userId).updateChildren(userMap).addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            Toast.makeText(WiewPro.this, "Vous avez ajouter "+nam+" comme un ami",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            String message= task.getException().getMessage();
                            Toast.makeText(WiewPro.this, "Erreur: "+message, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }));
    }
}