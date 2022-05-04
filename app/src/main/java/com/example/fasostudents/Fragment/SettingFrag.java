package com.example.fasostudents.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fasostudents.Profil.Profi;
import com.example.fasostudents.Cat.Projects;
import com.example.fasostudents.Cat.Setting;
import com.example.fasostudents.Constante.PassAct;
import com.example.fasostudents.R;

public class SettingFrag extends Fragment {

    private ListView lv;
    private String[] titles={"Profil","Projets","Récrutement","Paramètres","Conditions & Confidentialités",
    "A propos","Assistance"};
    private int[] images= {R.drawable.user,R.drawable.project,R.drawable.recrut, R.drawable.ic_settings, R.drawable.condition,
            R.drawable.image, R.drawable.assistance};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.setting_frag, container, false);

        lv= view.findViewById(R.id.list);
        MyAdapter adapter= new MyAdapter(getContext(), titles, images);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener((adapterView, view1, i, l) -> {
            switch (i){
                case 0:
                    new PassAct(getContext(), Profi.class);
                    break;
                case 1:
                    new PassAct(getContext(), Projects.class);
                    break;
                case 2:

                    break;
                case 3:
                    new PassAct(getContext(), Setting.class);
                    break;
                case 4:
                    break;
                case 5:
                    break;

            }
        });

        return view;
    }

    private class MyAdapter extends ArrayAdapter {
        int[] imgArray;
        String[] titleArray;

        public MyAdapter(Context context, String[] title1, int[] img1){
            super(context, R.layout.setting_row, R.id.idtitle, title1);
            this.imgArray= img1;
            this.titleArray= title1;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater= (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row= inflater.inflate(R.layout.setting_row, parent, false);

            ImageView imageView= row.findViewById(R.id.idimg);
            TextView myTitle= row.findViewById(R.id.idtitle);

            imageView.setImageResource(imgArray[position]);
            myTitle.setText(titleArray[position]);

            return row;
        }

    }
}
