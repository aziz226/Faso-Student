package com.example.fasostudents.Constante;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fasostudents.R;

import java.util.List;

public class AdapterPro extends RecyclerView.Adapter<AdapterPro.MyHolder> {
    Context context;
    List<ModelPro> list;

    public AdapterPro(Context context, List<ModelPro> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterPro.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.view_projects, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPro.MyHolder holder, int position) {
        String n, mbr, d;
        n= list.get(position).getNomPro();
        mbr= list.get(position).getMembres();
        d= list.get(position).getDescriptions();

        holder.np.setText(n);
        holder.mp.setText(mbr);
        holder.dp.setText(d);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView np, mp, dp;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            np= itemView.findViewById(R.id.id_prtname);
            mp= itemView.findViewById(R.id.mbrimg);
            dp= itemView.findViewById(R.id.prtdes);
        }
    }
}
