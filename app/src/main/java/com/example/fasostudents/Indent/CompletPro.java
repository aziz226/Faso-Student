package com.example.fasostudents.Indent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.fasostudents.Constante.Constant;
import com.example.fasostudents.Constante.IntAct;
import com.example.fasostudents.Main.MainActivity;
import com.example.fasostudents.R;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class CompletPro extends AppCompatActivity {

    private String[] items= {"2IE","CERPAMAD","ESCO-IGES","ESC","ESTA","ESMI",
    "ESMAT","ESUP","ESUPEX","HETEC","IST","ISGE BF","IAM(UUA)","ISTAPEM","ISPP","IIM","ISTA",
    "IFC","ISIIC","OUAGA 3S","SWIIS UMF","SOM","UAB","UTM","USTA","ULB","UPO","USATN","UPAM","Université du faso",
    "UNIVERSITE NAZI BONI","UNIVERSITE JOSEPH KI-ZERBO","UNIVERSITE NORBERT ZONGO","UNIVERSITE THOMAS SANKARA",
    "UNIVERSITE DE OUAHIGOUYA","UNIVERSITE DE DEDOUGOU","UNIVERSITE DE BANFORA",
    "UNIVERSITE DE GAOUA","UNIVERSITE DE DORI","UNIVERSITE DE KAYA",
    "UNIVERSITE DE TENKODOGO","UNIVERSITE DE FADA N'GOURMA"};

    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<String> adapterItem;
    private String univ, userId;
    private TextInputEditText n, p, v, d;
    private Button btn;
    private RelativeLayout rl;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private static int gallery_pick= 1;
    private StorageReference postRef;
    private CircleImageView cPProfil;
    private ProgressDialog loadingBar;
    private String postUrl, saveCurrentDate, saveCurrenTime, randomKey;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complet_pro);

        Toolbar toolbar= findViewById(R.id.ctb);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        // La base de donnes
        mAuth= FirebaseAuth.getInstance();
        userId= mAuth.getCurrentUser().getUid();
        userRef= FirebaseDatabase.getInstance().getReference().child(Constant.KEY_USER).child(userId);

        n= findViewById(R.id.cpn);
        p= findViewById(R.id.cpp);
        v= findViewById(R.id.cv);
        d= findViewById(R.id.cd);
        btn= findViewById(R.id.cc_btn);
        rl= findViewById(R.id. rl_ccbtn);
        cPProfil= findViewById(R.id.cmpimg);

        autoCompleteTextView= findViewById(R.id.auto_com);
        adapterItem= new ArrayAdapter<String>(this, R.layout.list_item, items);
        autoCompleteTextView.setAdapter(adapterItem);

        autoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
            univ= adapterView.getItemAtPosition(i).toString();
            Toast.makeText(CompletPro.this, ""+univ, Toast.LENGTH_SHORT).show();

        });

        cPProfil.setOnClickListener(view -> {
            Intent intent= new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, gallery_pick);
        });

        btn.setOnClickListener(view -> {
            btn.setVisibility(View.GONE);
            rl.setVisibility(View.VISIBLE);

            String no= n.getText().toString();
            String pr= p.getText().toString();
            String vl= v.getText().toString();
            String dm= d.getText().toString();
            String em= mAuth.getCurrentUser().getEmail();
            String username= no+" "+pr;

            if (TextUtils.isEmpty(no)){
                n.setError("Veuillez entrer votre nom");
                p.setError("Veuillez entrer votre prenom");
                v.setError("Veuillez entrer votre ville");
                d.setError("Veuillez entrer votre domaine d'etude");
            }else if (TextUtils.isEmpty(pr)){
                p.setError("Veuillez entrer votre prenom");
                v.setError("Veuillez entrer votre ville");
                d.setError("Veuillez entrer votre domaine d'etude");
            }else if (TextUtils.isEmpty(vl)){
                v.setError("Veuillez entrer votre ville");
                d.setError("Veuillez entrer votre domaine d'etude");
            }else if (TextUtils.isEmpty(dm)){
                d.setError("Veuillez entrer votre domaine d'etude");
            }else {

                HashMap userMap= new HashMap();

                userMap.put(Constant.KEY_USERNAME, username);
                userMap.put(Constant.KEY_VILLE, vl);
                userMap.put(Constant.KEY_DOMAINE, dm);
                userMap.put(Constant.KEY_UNIV, univ);
                userMap.put(Constant.KEY_ID, userId);
                userMap.put(Constant.KEY_EMAIL, em);
                userMap.put(Constant.KEY_PROIMG, postUrl);

                userMap.put(Constant.KEY_PRO_DESCR, "Inconnue");
                userMap.put(Constant.KEY_COMPETENCE, "Inconnue");
                userMap.put(Constant.KEY_EXPER, "Inconnue");

                userRef.updateChildren(userMap).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        btn.setVisibility(View.GONE);
                        rl.setVisibility(View.VISIBLE);
                        Toast.makeText(CompletPro.this, "Profil completé",
                                Toast.LENGTH_SHORT).show();
                        new IntAct(this, MainActivity.class);
                    }else {
                        String message= task.getException().getMessage();
                        Toast.makeText(this, "Erreur: "+message, Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified()){
            Toast.makeText(this, "Completer votre profil !",
                    Toast.LENGTH_SHORT).show();

        }/*else {
            Toast.makeText(this, "Veuillez verifier votre email !",
                    Toast.LENGTH_SHORT).show();
            new IntAct(this, verifAct.class);
        }*/

        cPProfil.setOnClickListener(view -> {
            Intent intent= new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, gallery_pick);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ProgressDialog loadingBar= new ProgressDialog(this);
        loadingBar.setTitle("Sauvegarde de la photo en cours...");
        loadingBar.setMessage("Veuillez patienter");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        imageUri= data.getData();
        cPProfil.setImageURI(imageUri);

        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat currentDate= new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate= currentDate.format(calendar.getTime());

        Calendar calendar1= Calendar.getInstance();
        SimpleDateFormat currentTime= new SimpleDateFormat("HH:mm");
        saveCurrenTime= currentTime.format(calendar1.getTime());

        randomKey= saveCurrentDate+saveCurrenTime;

        postRef= FirebaseStorage.getInstance().getReference()
                .child(Constant.KEY_PROIMG).child(imageUri.getLastPathSegment()+randomKey+".JPG");

        postRef.putFile(imageUri).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Task<Uri> uriTask = task.getResult().getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful()) ;
                Uri downloadUrl = uriTask.getResult();

                postUrl = downloadUrl.toString();
                Toast.makeText(this, "Sauvegarde reussie...", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }else {
                String message= task.getException().getMessage();
                Toast.makeText(this, "Erreur: "+message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        });
    }
}