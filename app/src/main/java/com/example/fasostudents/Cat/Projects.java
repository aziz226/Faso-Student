package com.example.fasostudents.Cat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.fasostudents.Constante.AdapterPro;
import com.example.fasostudents.Constante.Constant;
import com.example.fasostudents.Constante.ModelPro;
import com.example.fasostudents.Constante.PassAct;
import com.example.fasostudents.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Projects extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterPro adapterPro;
    private List<ModelPro> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.projects);

        Toolbar toolbar= findViewById(R.id.pttb);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        recyclerView= findViewById(R.id.recy_pro);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        list= new ArrayList<>();

        GetAllProject();

    }

    private void GetAllProject() {
        FirebaseUser fAuth= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userRef= FirebaseDatabase.getInstance().getReference().child(Constant.KEY_PROJECTS);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    ModelPro modelPro= ds.getValue(ModelPro.class);

                    list.add(modelPro);

                    adapterPro= new AdapterPro(getApplicationContext(), list);
                    recyclerView.setAdapter(adapterPro);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    public void AddProject(View view) {
        new PassAct(getApplicationContext(), ShareProject.class);
    }
}