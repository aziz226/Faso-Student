package com.example.fasostudents.Indent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.fasostudents.Constante.IntAct;
import com.example.fasostudents.Constante.PassAct;
import com.example.fasostudents.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class verifAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verif);

        Toolbar toolbar= findViewById(R.id.vrtb);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
    }

    public void rsendEmail(View view) {
        RelativeLayout layout= findViewById(R.id.rl_vrt);
        Button btn= findViewById(R.id.resnd_btn);

        btn.setVisibility(View.GONE);
        layout.setVisibility(View.VISIBLE);

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Toast.makeText(verifAct.this, "L'email de verification a été envoyé à nouveau !",
                        Toast.LENGTH_SHORT).show();
                btn.setVisibility(View.VISIBLE);
                layout.setVisibility(View.GONE);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if (user.isEmailVerified()){
            new IntAct(getApplicationContext(), ConAct.class);
        }
    }
}