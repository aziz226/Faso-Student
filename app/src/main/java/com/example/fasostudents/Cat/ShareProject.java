package com.example.fasostudents.Cat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.fasostudents.Constante.Constant;
import com.example.fasostudents.Constante.PassAct;
import com.example.fasostudents.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ShareProject extends AppCompatActivity {

    private Button btn;
    private RelativeLayout rl;
    private TextInputEditText pn, pm, pc, pd;
    private FirebaseAuth mAuht;
    private String userId;
    private DatabaseReference userRef, userRef1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_project);

        btn= findViewById(R.id.btn_sharePro);
        rl= findViewById(R.id.rl_btnSharePro);

        pn= findViewById(R.id.prtn);
        pm= findViewById(R.id.prtmbr);
        pc= findViewById(R.id.prtcmp);
        pd= findViewById(R.id.prodesc);

        mAuht= FirebaseAuth.getInstance();
        userId= mAuht.getCurrentUser().getUid();
        userRef= FirebaseDatabase.getInstance().getReference().child(Constant.KEY_USER)
                .child(userId).child(Constant.KEY_PROJECTS);
        userRef1= FirebaseDatabase.getInstance().getReference().child(Constant.KEY_PROJECTS);

        btn.setOnClickListener(view -> {
            String n, mrb, cmp, des;
            n= pn.getText().toString();
            mrb= pm.getText().toString();
            cmp= pc.getText().toString();
            des= pd.getText().toString();

            if (TextUtils.isEmpty(n)){
                pn.setError("Veuillez donnner le nom de votre projet!");
            }else if (TextUtils.isEmpty(mrb)){
                pm.setError("Veuillez donner le nombre de membre!");
            }else if (TextUtils.isEmpty(cmp)){
                pc.setError("Veuillez donner les competences requises pour le projet");
            }else if (TextUtils.isEmpty(des)){
                pd.setError("Veuillez donner quelques description du projet");
            }else {
                btn.setVisibility(View.GONE);
                rl.setVisibility(View.VISIBLE);

                Calendar calendar= Calendar.getInstance();
                SimpleDateFormat currentDate= new SimpleDateFormat("dd-MMMM-yyyy");
                String saveCurrentDate= currentDate.format(calendar.getTime());

                Calendar calendar1= Calendar.getInstance();
                SimpleDateFormat currentTime= new SimpleDateFormat("HH:mm");
                String saveCurrenTime= currentTime.format(calendar1.getTime());

                String randomKey= saveCurrentDate+saveCurrenTime;

                HashMap userMap= new HashMap();
                userMap.put("NomPro", n);
                userMap.put("Membres", mrb);
                userMap.put("CompetencesRequises", cmp);
                userMap.put("Descriptions", des);

                userRef.child(userId+randomKey).updateChildren(userMap)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()){
                                userRef1.child(userId+randomKey).updateChildren(userMap)
                                        .addOnCompleteListener(task1 -> {
                                            if (task1.isSuccessful()){

                                                new PassAct(getApplicationContext(), Projects.class);

                                                btn.setVisibility(View.VISIBLE);
                                                rl.setVisibility(View.GONE);

                                                Toast.makeText(this, "Projet publie avec succes",
                                                        Toast.LENGTH_SHORT).show();
                                            }else {
                                                btn.setVisibility(View.VISIBLE);
                                                rl.setVisibility(View.GONE);

                                                String msg= task1.getException().getMessage();
                                                Toast.makeText(this, "erreur: "+msg, Toast.LENGTH_SHORT)
                                                        .show();
                                            }
                                        });
                            }else {
                                btn.setVisibility(View.VISIBLE);
                                rl.setVisibility(View.GONE);

                                String message= task.getException().getMessage();
                                Toast.makeText(this, "Erreur: "+message, Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });

    }
}