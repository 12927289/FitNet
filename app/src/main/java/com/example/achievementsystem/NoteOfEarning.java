package com.example.achievementsystem;

import android.support.design.widget.Snackbar;
import android.view.View;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class  NoteOfEarning{
    public static Snackbar snackbar(View view, String message){
        Snackbar snackbar = Snackbar.make(view,message,LENGTH_LONG);
        return snackbar;
    }
}
