package com.example.fasostudents.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.fasostudents.Constante.Constant;
import com.example.fasostudents.Constante.IntAct;
import com.example.fasostudents.Fragment.ChatFrag;
import com.example.fasostudents.Fragment.FriendFrag;
import com.example.fasostudents.Fragment.HomeFrag;
import com.example.fasostudents.Fragment.NotifFrag;
import com.example.fasostudents.Fragment.SettingFrag;
import com.example.fasostudents.Indent.Acceuille;
import com.example.fasostudents.Indent.CompletPro;
import com.example.fasostudents.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private MeowBottomNavigation bottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation= findViewById(R.id.bottom_nav);

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_friend));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_forum));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.home));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_notif));
        bottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.ic_menu));

        bottomNavigation.setOnShowListener(item -> {
            Fragment fragment= null;
            switch (item.getId()){
                case 1:
                    fragment= new FriendFrag();
                    break;
                case 2:
                    fragment= new ChatFrag();
                    break;
                case 3:
                    fragment= new HomeFrag();
                    break;
                case 4:
                    fragment= new NotifFrag();
                    break;
                case 5:
                    fragment= new SettingFrag();
                    break;
            }
            loadFrag(fragment);
        });

        bottomNavigation.setCount(4, "10");
        bottomNavigation.show(3, true);
        bottomNavigation.setOnClickMenuListener(item -> {

        });
        bottomNavigation.setOnReselectListener(item ->
                Toast.makeText(MainActivity.this, "Reclique sur le ", Toast.LENGTH_SHORT).show());
    }

    private void loadFrag(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth fAuth= FirebaseAuth.getInstance();
        FirebaseUser currentUser= fAuth.getCurrentUser();


        if (currentUser== null){
            new IntAct(this, Acceuille.class);
        }else {
            String userId= fAuth.getCurrentUser().getUid();
            DatabaseReference ref= FirebaseDatabase.getInstance().getReference()
                    .child(Constant.KEY_USER).child(userId);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()){
                        Toast.makeText(MainActivity.this, "Veuillez completer votre profil !",
                                Toast.LENGTH_SHORT).show();
                        new IntAct(getApplicationContext(), CompletPro.class);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }
}