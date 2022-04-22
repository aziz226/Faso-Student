package com.example.fasostudents.Constante;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fasostudents.Profil.WiewPro;
import com.example.fasostudents.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.MyHolder> {

    Context context;
    List<ModelUser> userList;

    public AdapterUser(Context context, List<ModelUser> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public AdapterUser.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_user, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUser.MyHolder holder, int position) {
        String uname, domaine, userimg, inst, id;
        uname= userList.get(position).getUserName();
        domaine= userList.get(position).getDomaine();
        userimg= userList.get(position).getProfilImg();
        inst= userList.get(position).getUniversite();
        id= userList.get(position).getId();

        holder.name.setText(uname);
        holder.dom.setText(domaine);
        holder.unv.setText(inst);

        try{
            Picasso.with(context).load(userimg).placeholder(R.drawable.img).into(holder.img);
        }catch (Exception e){ }

        holder.itemView.setOnClickListener(view -> {
            Intent intent= new Intent(context, WiewPro.class);
            intent.putExtra("id", id);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    protected class MyHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView name, dom, unv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            img= itemView.findViewById(R.id.cpimg);
            name= itemView.findViewById(R.id.row_un);
            dom= itemView.findViewById(R.id.row_dm);
            unv= itemView.findViewById(R.id.row_sch);

        }
    }
}
