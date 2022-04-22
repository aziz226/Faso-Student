package com.example.fasostudents.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fasostudents.Constante.AdapterUser;
import com.example.fasostudents.Constante.Constant;
import com.example.fasostudents.Constante.ModelUser;
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

public class FriendFrag extends Fragment {

    private RecyclerView recyclerView;
    private AdapterUser adapterUser;
    private List<ModelUser> userList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.friend_frag, container, false);

        recyclerView= view.findViewById(R.id.recyc_user);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        userList= new ArrayList<>();

        getAllUsers();

        return view;
    }

    private void getAllUsers() {
        FirebaseUser fUsers= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child(Constant.KEY_USER);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    ModelUser modelUser= ds.getValue(ModelUser.class);

                    if (!modelUser.getId().equals(fUsers.getUid())){
                        userList.add(modelUser);
                    }

                    adapterUser= new AdapterUser(getActivity(), userList);

                    recyclerView.setAdapter(adapterUser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
