package com.example.fasostudents.Indent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.fasostudents.Constante.IntAct;
import com.example.fasostudents.Constante.PassAct;
import com.example.fasostudents.Main.MainActivity;
import com.example.fasostudents.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ConAct extends AppCompatActivity {

    private TextInputEditText cEm, cPs;
    private Button cBtn;
    private RelativeLayout rl;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_con);

        Toolbar toolbar= findViewById(R.id.ctoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        cEm= findViewById(R.id.con_em);
        cPs= findViewById(R.id.con_pass);
        cBtn= findViewById(R.id.con_btn);
        rl= findViewById(R.id.rl_btn);

        mAuth= FirebaseAuth.getInstance();

        cBtn.setOnClickListener(view -> {
            String em= cEm.getText().toString();
            String ps= cPs.getText().toString();
            if (TextUtils.isEmpty(em)){
                cEm.setError("Veuillez entrer votre email!");
                cPs.setError("Veuillez entrer votre mot de passe!");
            }else if (TextUtils.isEmpty(ps)){
                cPs.setError("Veuillez entrer votre mot de passe!");
            }else {
                cBtn.setVisibility(View.GONE);
                rl.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(em, ps)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()){
                                cBtn.setVisibility(View.VISIBLE);
                                rl.setVisibility(View.GONE);
                                new IntAct(this, MainActivity.class);
                            }else {
                                String message= task.getException().getMessage();
                                cBtn.setVisibility(View.VISIBLE);
                                rl.setVisibility(View.GONE);
                                Toast.makeText(this, "Erreur: "+message, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }

    public void PassOubl(View view) {
        new PassAct(this, ResetPass.class);
    }
}