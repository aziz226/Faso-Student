package com.example.fasostudents.Constante;

import android.content.Context;
import android.content.Intent;

public class IntAct {
    Context context;
    Class aClass;

    public IntAct(Context context, Class aClass) {
        this.context = context;
        this.aClass = aClass;

        Intent intent= new Intent(context, aClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}
