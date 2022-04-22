package com.example.fasostudents.Constante;

import android.content.Context;
import android.content.Intent;

public class PassAct {
    Context context;
    Class aClass;

    public PassAct(Context context, Class aClass) {
        this.context = context;
        this.aClass = aClass;

        Intent intent= new Intent(context, aClass);
        context.startActivity(intent);
    }
}
