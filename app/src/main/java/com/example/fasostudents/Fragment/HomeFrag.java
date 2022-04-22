package com.example.fasostudents.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fasostudents.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFrag extends Fragment {

   private FloatingActionButton postbtn, writbtn, addbtn, closbtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.home_frag, container, false);

        postbtn= view.findViewById(R.id.idflpostbtn);
        writbtn= view.findViewById(R.id.idflwritbtn);
        addbtn= view.findViewById(R.id.idaddpostbtn);
        closbtn= view.findViewById(R.id.idflclosbtn);

        addbtn.setOnClickListener(view1 -> {
            addbtn.setVisibility(View.GONE);
            closbtn.setVisibility(View.VISIBLE);
            postbtn.setVisibility(View.VISIBLE);
            writbtn.setVisibility(View.VISIBLE);
        });

        closbtn.setOnClickListener(view12 -> {
            addbtn.setVisibility(View.VISIBLE);
            closbtn.setVisibility(View.GONE);
            postbtn.setVisibility(View.GONE);
            writbtn.setVisibility(View.GONE);
        });

        return view;
    }
}
