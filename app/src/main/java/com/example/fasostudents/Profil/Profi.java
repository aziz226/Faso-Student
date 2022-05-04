package com.example.fasostudents.Profil;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.fasostudents.Constante.Constant;
import com.example.fasostudents.Constante.PassAct;
import com.example.fasostudents.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

import java.util.HashMap;
import java.util.Objects;

public class Profi extends AppCompatActivity {

    private TextView usn, usc, usf, udes;
    private ImageView proImg;
    private final static int gallery_pick= 1;
    private String postUrl;
    private String nam, sc, fil, img, desc;
    private DatabaseReference userRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profi);

        Toolbar toolbar= findViewById(R.id.ptb);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        usn= findViewById(R.id.pun);
        usc= findViewById(R.id.psc);
        usf= findViewById(R.id.pf);
        proImg= findViewById(R.id.id_img);
        udes= findViewById(R.id.idprodesc);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child(Constant.KEY_USER).child(userId);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    nam= Objects.requireNonNull(snapshot.child(Constant.KEY_USERNAME).getValue()).toString();
                    sc= Objects.requireNonNull(snapshot.child(Constant.KEY_UNIV).getValue()).toString();
                    fil= Objects.requireNonNull(snapshot.child(Constant.KEY_DOMAINE).getValue()).toString();
                    img= Objects.requireNonNull(snapshot.child(Constant.KEY_PROIMG).getValue()).toString();
                    desc= Objects.requireNonNull(snapshot.child(Constant.KEY_PRO_DESCR).getValue()).toString();

                    usn.setText(nam);
                    usc.setText(sc);
                    usf.setText(fil);
                    udes.setText(desc);

                    try {
                        Picasso.with(getApplicationContext()).load(img).placeholder(R.drawable.image).into(proImg);
                    }catch (Exception ignored){}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }});

    }

    public void btnModifyPro(View view) {
        String[] options= {"La photo de profil","Le nom et Prenom","L'institue"
                ,"La filiere","La competence","L'experience","La description"};

        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Modification du profil");
        builder.setItems(options, (dialogInterface, i) -> {
            switch (i){
                case 0:
                    Intent intent= new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, gallery_pick);

                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ProgressDialog dialog= new ProgressDialog(this);
        dialog.setTitle("Changement de photo du profil en cours");
        dialog.setMessage("Veuilllez patienter...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

            Uri imageUri= data.getData();

           StorageReference postRef= FirebaseStorage.getInstance().getReference()
                    .child(Constant.KEY_PROIMG).child(imageUri.getLastPathSegment()+ra+".JPG");

           postRef.putFile(imageUri).addOnCompleteListener(task -> {
               if (task.isSuccessful()){
                   Task<Uri> uriTask= task.getResult().getStorage().getDownloadUrl();
                   while (!uriTask.isSuccessful());
                   String downloadUrl= uriTask.getResult().toString();

                   HashMap userMap= new HashMap();
                   userMap.put(downloadUrl, Constant.KEY_PROIMG);
                   userRef.updateChildren(userMap).addOnCompleteListener(task1 -> {
                       if (task1.isSuccessful()){
                           Toast.makeText(this, "Modification reussie!", Toast.LENGTH_SHORT).show();
                           dialog.dismiss();
                       }else {
                           String message= task1.getException().getMessage();
                           Toast.makeText(this, "Erreur: "+message, Toast.LENGTH_SHORT).show();
                           dialog.dismiss();
                       }
                   });
               }else {
                   String message= task.getException().getMessage();
                   Toast.makeText(this, "Erreur: "+message, Toast.LENGTH_SHORT).show();
                   dialog.dismiss();
               }
           });

    }

    public void friendbtn(View view) {
        new PassAct(this, MesAmis.class);
    }
}