package com.edubox.admin.sms;

import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.edubox.admin.R;

import java.util.HashMap;
import java.util.Map;

public class sms extends AppCompatActivity {

    private Map<String, String> userMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);


        Intent intent = getIntent();

// Get the map from the intent extras
        userMessages = (Map<String, String>) ((Intent) intent).getSerializableExtra("userMessages");

        // Map user phone numbers to their respective messages
        userMessages = new HashMap<>();
        userMessages.put("1234567890", "Hello User1!"); // Add the phone numbers and messages here
        userMessages.put("9876543210", "Hi User2!");

        try {
            SmsManager smsManager = SmsManager.getDefault();
            for (Map.Entry<String, String> entry : userMessages.entrySet()) {
                String phoneNumber = entry.getKey();
                String message = entry.getValue();
                smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            }
            Toast.makeText(this, "SMS sent successfully to multiple users", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Failed to send SMS", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
