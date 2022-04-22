package com.example.fasostudents.Indent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fasostudents.Constante.IntAct;
import com.example.fasostudents.Constante.PassAct;
import com.example.fasostudents.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreeAct extends AppCompatActivity {

    private TextInputEditText em, pas, cPas;
    private Button cbtn, rebtn;
    private TextView msg;
    private RelativeLayout rl;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cree);

        Toolbar toolbar= findViewById(R.id.ctb);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        mAuth= FirebaseAuth.getInstance();

        em= findViewById(R.id.cc_em);
        pas= findViewById(R.id.cc_pass);
        cPas= findViewById(R.id.ccf_pass);
        cbtn= findViewById(R.id.cc_btn);
        rl= findViewById(R.id.rl_ccbtn);
        rebtn= findViewById(R.id.resnd_btn);
        msg= findViewById(R.id.veri_messag);

        cbtn.setOnClickListener(view -> {
            String ema= em.getText().toString();
            String pass= pas.getText().toString();
            String cPass= cPas.getText().toString();

            if (TextUtils.isEmpty(ema)){
                em.setError("Veuillez entrer votre email!");
                pas.setError("Veuillez entrer votre mot de passe!");
                cPas.setError("Veuillez confirmer votre de passe");
            }else if (TextUtils.isEmpty(pass)){
                pas.setError("Veuillez entrer votre de passe!");
                cPas.setError("Veuillez confirmer votre mot de passe!");
            }else if (pass.length()<8){
                pas.setError("Le mot de passe doit être supérieur 8 caractères");
            }else if (TextUtils.isEmpty(cPass)){
                cPas.setError("Veuillez confirmer votre email!");
            }else if (!pass.equals(cPass)){
                cPas.setError("Votre mot de passe ne se correspond pas !");
            }else {
                cbtn.setVisibility(View.GONE);
                rl.setVisibility(View.VISIBLE);
               mAuth.createUserWithEmailAndPassword(ema, pass)
                       .addOnCompleteListener(task -> {
                           if (task.isSuccessful()){
                               FirebaseUser user= mAuth.getCurrentUser();
                               user.sendEmailVerification().addOnCompleteListener(task1 -> {
                                   if (task1.isSuccessful()){
                                       Toast.makeText(this, "Un email de vérificqtion vous a été envoyé",
                                               Toast.LENGTH_SHORT).show();
                                       rl.setVisibility(View.GONE);
                                      cbtn.setVisibility(View.VISIBLE);
                                      new IntAct(this, verifAct.class);

                                   }
                               });
                           }
                       });
            }
        });
    }
}