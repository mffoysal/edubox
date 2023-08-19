package com.edubox.admin.subject;

import android.os.Bundle;

import com.edubox.admin.BaseMenu;
import com.edubox.admin.R;

public class SubjectPanel extends BaseMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_panel);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }
}