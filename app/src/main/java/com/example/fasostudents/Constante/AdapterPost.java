package com.example.fasostudents.Constante;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fasostudents.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterPost extends RecyclerView.Adapter<AdapterPost.MyHolder>{

    Context context;
    List<ModelPost> list;

    public AdapterPost(Context context, List<ModelPost> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_post, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String Name, Date, Time, Desc, Pimg, Postimg;

        Name= list.get(position).getUserName();
        Date= list.get(position).getDate();
        Time= list.get(position).getHeure();
        Desc= list.get(position).getPub_description();
        Pimg= list.get(position).getProfilImg();
        Postimg= list.get(position).getPostUrl();

        holder.name.setText(Name);
        holder.date.setText(Date);
        holder.time.setText(Time);
        holder.desc.setText(Desc);

        try{

            Picasso.with(context).load(Pimg).placeholder(R.drawable.img).into(holder.pimg);
        }catch (Exception e){}

        try {
            Picasso.with(context).load(Postimg).placeholder(R.drawable.post).into(holder.postImg);
        }catch (Exception e){ }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView name, date, time, desc;
        CircleImageView pimg;
        ImageView postImg;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            name= itemView.findViewById(R.id.row_pun);
            date= itemView.findViewById(R.id.row_pdate);
            time= itemView.findViewById(R.id.row_ptime);
            desc= itemView.findViewById(R.id.idposdes);
            pimg= itemView.findViewById(R.id.idpim);
            postImg= itemView.findViewById(R.id.idpospho);
        }
    }
}
