package com.example.mobile.helper;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mobile.R;

public class MenuMain {
    public static  void Init(AppCompatActivity activity, int id){
        Toolbar toolbar = activity.findViewById(id);
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Studio Ghibli");
    }
}
