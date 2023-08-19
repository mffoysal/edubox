package com.edubox.admin.cls;

import android.os.Bundle;

import com.edubox.admin.BaseMenu;
import com.edubox.admin.R;

public class ClassPanel extends BaseMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_panel);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}