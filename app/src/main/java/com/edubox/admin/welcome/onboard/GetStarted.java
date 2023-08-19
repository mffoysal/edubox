package com.edubox.admin.welcome.onboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.edubox.admin.Logout;
import com.edubox.admin.R;
import com.edubox.admin.login.LoginMainActivity;

public class GetStarted extends AppCompatActivity {
    Button startButton;
    private Logout logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
    
        logout = new Logout(getApplicationContext());
        startButton = findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout.setInstaller(true);
                Intent i = new Intent(GetStarted.this, LoginMainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}