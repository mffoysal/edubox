package com.edubox.admin.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.edubox.admin.BaseMenu;
import com.edubox.admin.R;
import com.edubox.admin.view.ViewOne;

public class Welcome extends BaseMenu implements View.OnClickListener {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_welcome);

        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                intent = new Intent(getApplicationContext(), ViewOne.class);
                startActivity(intent);
                finish();
            }
        }, 4000);


    }

    @Override
    public void onClick(View view) {

    }
}