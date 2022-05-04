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

import com.example.fasostudents.Constante.AdapterPost;
import com.example.fasostudents.Constante.Constant;
import com.example.fasostudents.Constante.ModelPost;
import com.example.fasostudents.Constante.PassAct;
import com.example.fasostudents.Posts.PostImg;
import com.example.fasostudents.Posts.PostWrit;
import com.example.fasostudents.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFrag extends Fragment {

   private FloatingActionButton postbtn, writbtn, addbtn, closbtn;
   private RecyclerView recyclerView;
   private AdapterPost adapterPost;
   private List<ModelPost> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.home_frag, container, false);

        postbtn= view.findViewById(R.id.idflpostbtn);
        writbtn= view.findViewById(R.id.idflwritbtn);
        addbtn= view.findViewById(R.id.idaddpostbtn);
        closbtn= view.findViewById(R.id.idflclosbtn);
        recyclerView= view.findViewById(R.id.idpostlist);

        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        list= new ArrayList<>();

        getAllPost();

        addbtn.setOnClickListener(view1 -> {
            addbtn.setVisibility(View.GONE);
            closbtn.setVisibility(View.VISIBLE);
            postbtn.setVisibility(View.VISIBLE);
            writbtn.setVisibility(View.VISIBLE);
        });

        postbtn.setOnClickListener(view13 -> new PassAct(getContext(), PostImg.class));

        writbtn.setOnClickListener(view14 -> new PassAct(getActivity(), PostWrit.class));

        closbtn.setOnClickListener(view12 -> {
            addbtn.setVisibility(View.VISIBLE);
            closbtn.setVisibility(View.GONE);
            postbtn.setVisibility(View.GONE);
            writbtn.setVisibility(View.GONE);
        });



        return view;
    }

    private void getAllPost() {
        FirebaseUser fUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userRef= FirebaseDatabase.getInstance().getReference().child(Constant.KEY_POSTS);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot ds:snapshot.getChildren()){
                    ModelPost modelPost= ds.getValue(ModelPost.class);
                 //   if (!modelPost.getId().equals(fUser.getUid())){ }
                    list.add(modelPost);
                    adapterPost= new AdapterPost(getActivity(), list);
                    recyclerView.setAdapter(adapterPost);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
}
