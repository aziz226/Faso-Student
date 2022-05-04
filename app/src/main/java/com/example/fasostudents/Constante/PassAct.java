package com.example.fasostudents.Constante;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class PassAct {
    Context context;
    Class aClass;

    public PassAct(Context context, Class aClass) {
        this.context = context;
        this.aClass = aClass;

        Intent intent= new Intent(context, aClass);
        Bundle b =ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle();
        context.startActivity(intent, b);
    }
}
