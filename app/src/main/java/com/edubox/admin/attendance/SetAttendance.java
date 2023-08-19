package com.edubox.admin.attendance;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.edubox.admin.R;

public class SetAttendance extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_attendance);

        String qrCodeContent = getIntent().getStringExtra("eduboxId");

        Toast.makeText(this,"Your Id : "+qrCodeContent,Toast.LENGTH_SHORT).show();


    }
}