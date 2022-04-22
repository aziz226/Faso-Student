package com.example.fasostudents.Indent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.fasostudents.Constante.IntAct;
import com.example.fasostudents.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPass extends AppCompatActivity {

    private Button btn;
    private TextInputEditText em;
    private RelativeLayout rl;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

        Toolbar toolbar= findViewById(R.id.rstoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        btn= findViewById(R.id.rs_btn);
        em= findViewById(R.id.rst_em);
        rl= findViewById(R.id.rsl_btn);

        mAuth= FirebaseAuth.getInstance();

        btn.setOnClickListener(view -> {
            btn.setVisibility(View.GONE);
            rl.setVisibility(View.VISIBLE);
            String email= em.getText().toString();
            if (TextUtils.isEmpty(email)){
                em.setError("Veuillez entrer votre email");
            }else{
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        btn.setVisibility(View.VISIBLE);
                        rl.setVisibility(View.GONE);
                        new IntAct(this, Acceuille.class);
                    }
                });
            }
        });

    }
}