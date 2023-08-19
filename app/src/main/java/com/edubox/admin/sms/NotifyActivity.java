package com.edubox.admin.sms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.edubox.admin.AllUsersActivity;
import com.edubox.admin.Logout;
import com.edubox.admin.ProfileActivity;
import com.edubox.admin.R;
import com.edubox.admin.scanner.ScanActivity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class NotifyActivity extends AppCompatActivity {
    private TextView textView;
    private Intent intent;
    private ActionBar actionbar;
    private Logout logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        logout = new Logout(getApplicationContext());

        textView = findViewById(R.id.notifyId);

        intent = getIntent();
        actionbar = getSupportActionBar();
        if (intent != null && intent.hasExtra("user")) {
            String url = intent.getStringExtra("user");
            actionbar.setTitle(actionbar.getTitle()+" "+url);
        }else {
//            logout.setLoggedUser("015858550755","01816444372");
            actionbar.setTitle(actionbar.getTitle()+" U: "+logout.getStringPreference("userId"));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_layout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.settingId){
            return true;
        } else if (item.getItemId()==R.id.profileId) {

            Intent intent = new Intent(NotifyActivity.this, ProfileActivity.class);
            intent.putExtra("profile","Farhad Foysal\n+8801585855075");
            startActivity(intent);

            return true;
        }else if (item.getItemId()==R.id.allUserId) {

            Intent intent = new Intent(NotifyActivity.this, AllUsersActivity.class);
            intent.putExtra("users","01585855075");
            startActivity(intent);

            return true;
        }else if (item.getItemId()==R.id.aboutId) {

            Intent intent = new Intent(NotifyActivity.this, ScanActivity.class);
            intent.putExtra("users","01585855075");
            startActivity(intent);

            return true;
        }else if (item.getItemId()==R.id.menuId) {
            NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext(),"FarhadFoysal");
            notificationHelper.showBigTextNotification("Edubox","Hello farhad foysal","Farid Ahmed\nRojina Akter\nSanjida Farid Najifa","FarhadFoysal");
            return true;
        }else if (item.getItemId()==R.id.shareId) {
            Map<String, String> msg = new HashMap<>();
            msg.put("01585855075","Hello Farhad Foysal");

            Intent intent = new Intent(NotifyActivity.this, sendSmss.class);
            intent.putExtra("user","");
            intent.putExtra("userMessages", (Serializable) msg);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}