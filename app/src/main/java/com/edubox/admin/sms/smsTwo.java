package com.edubox.admin.sms;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.core.app.ActivityCompat;

import com.edubox.admin.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class smsTwo extends AppCompatActivity {

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
        userMessages.put("01585855075", "Hello User1!"); // Add the phone numbers and messages here
//        userMessages.put("9876543210", "Hi User2!");

        // Check for the required permissions
        if (checkPermissions()) {
            sendMessages();
        } else {
            requestPermissions();
        }
    }

    private boolean checkPermissions() {
        int smsPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        int phoneStatePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        return smsPermission == PackageManager.PERMISSION_GRANTED &&
                phoneStatePermission == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE},
                123);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendMessages();
            } else {
                Toast.makeText(getApplicationContext(), "Permissions denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sendMessages() {
        SmsManager smsManager = SmsManager.getDefault();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        List<SubscriptionInfo> subscriptionInfoList = SubscriptionManager.from(this).getActiveSubscriptionInfoList();
        if (subscriptionInfoList != null && subscriptionInfoList.size() > 0) {
            for (Map.Entry<String, String> entry : userMessages.entrySet()) {
                String phoneNumber = entry.getKey();
                String message = entry.getValue();
                for (SubscriptionInfo subscriptionInfo : subscriptionInfoList) {
                    int subscriptionId = subscriptionInfo.getSubscriptionId();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        smsManager.sendTextMessage(phoneNumber, null, message, null, null, subscriptionId);
                    }
                }
            }
            Toast.makeText(getApplicationContext(), "SMS sent successfully to multiple users", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "No SIM card available", Toast.LENGTH_SHORT).show();
        }
    }
}
