package com.example.fasostudents.Cat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.fasostudents.Constante.IntAct;
import com.example.fasostudents.Indent.Acceuille;
import com.example.fasostudents.R;
import com.google.firebase.auth.FirebaseAuth;

public class Setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
    }

    public void Deconnexion(View view) {
        FirebaseAuth mAuth= FirebaseAuth.getInstance();
        mAuth.signOut();
        new IntAct(this, Acceuille.class);
    }
}