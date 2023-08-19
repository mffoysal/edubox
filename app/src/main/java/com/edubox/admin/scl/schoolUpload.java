package com.edubox.admin.scl;

import android.os.Bundle;

import com.edubox.admin.BaseMenu;
import com.edubox.admin.R;

public class schoolUpload extends BaseMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_upload);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}