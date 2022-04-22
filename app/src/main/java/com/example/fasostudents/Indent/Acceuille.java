package com.example.fasostudents.Indent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.fasostudents.Constante.PassAct;
import com.example.fasostudents.R;

public class Acceuille extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuille);
    }

    public void ConAct(View view) {
        new PassAct(this, ConAct.class);
    }

    public void CrerAct(View view) {
        new PassAct(this, CreeAct.class);
    }
}