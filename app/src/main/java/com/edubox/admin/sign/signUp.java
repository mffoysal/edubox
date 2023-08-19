package com.edubox.admin.sign;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.edubox.admin.R;
import com.edubox.admin.login.signIn;


public class signUp extends AppCompatActivity {


    //Variables
    ImageView backBtn;
    Button next, login;
    TextView titleText, slideText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up2);


        //Hooks for animation
        backBtn = findViewById(R.id.signup_back_button);
        next = findViewById(R.id.signup_next_button);
        login = findViewById(R.id.signup_login_button);
        titleText = findViewById(R.id.signup_title_text);
        slideText = findViewById(R.id.signup_slide_text);


    }


    public void callNextSigupScreen(View view) {


        Intent intent = new Intent(getApplicationContext(), SignUp2ndClass.class);


        //Add Shared Animation
        Pair[] pairs = new Pair[5];
        pairs[0] = new Pair<View, String>(backBtn, "transition_back_arrow_btn");
        pairs[1] = new Pair<View, String>(next, "transition_next_btn");
        pairs[2] = new Pair<View, String>(login, "transition_login_btn");
        pairs[3] = new Pair<View, String>(titleText, "transition_title_text");
        pairs[4] = new Pair<View, String>(slideText, "transition_slide_text");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(signUp.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }


    }


    public void callLoginFromSignUp(View view) {
        startActivity(new Intent(getApplicationContext(), signIn.class));
        finish();
    }


}