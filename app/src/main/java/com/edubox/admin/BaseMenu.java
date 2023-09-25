package com.edubox.admin;

import android.content.Intent;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.edubox.admin.scanner.ScanActivity;
import com.edubox.admin.scl.SchoolPanel;
import com.edubox.admin.settings.Permission;
import com.edubox.admin.sms.NotificationHelper;
import com.edubox.admin.sms.sendSmss;
import com.edubox.admin.update.AppUpdate;
import com.edubox.admin.web.WifiWeb;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BaseMenu extends AppCompatActivity {

    private Intent intent;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);

        MenuItem menuItem = menu.findItem(R.id.fsettingId);
        MenuItem menuItem2 = menu.findItem(R.id.flogoutId);
        MenuItem menuItem3 = menu.findItem(R.id.profileId);
        MenuItem menuItem4 = menu.findItem(R.id.allUserId);
        MenuItem menuItem5 = menu.findItem(R.id.menuId);
        MenuItem menuItem6 = menu.findItem(R.id.settingId);
        MenuItem menuItem7 = menu.findItem(R.id.shareId);
        MenuItem menuItem8 = menu.findItem(R.id.aboutId);
        MenuItem menuItem9 = menu.findItem(R.id.setSettings);
        MenuItem menuItem10 = menu.findItem(R.id.wifiWebMenu);
        MenuItem menuItem11 = menu.findItem(R.id.updateAppMenu);
//        SpannableString spannableString = new SpannableString(menuItem.getTitle());
//        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.menu_item_color)),
//                0, spannableString.length(), 0);
//        menuItem.setTitle(spannableString);
//
//        SpannableString spannableString2 = new SpannableString(menuItem2.getTitle());
//        spannableString2.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.menu_item_color)),
//                0, spannableString2.length(), 0);
//        menuItem2.setTitle(spannableString2);

        setColor(menuItem);
        setColor(menuItem2);
        setColor(menuItem3);
        setColor(menuItem4);
        setColor(menuItem5);
        setColor(menuItem6);
        setColor(menuItem7);
        setColor(menuItem8);
        setColor(menuItem9);
        setColor(menuItem10);
        setColor(menuItem11);
        return true;
    }

    public void setColor(MenuItem menuItem){
        SpannableString spannableString = new SpannableString(menuItem.getTitle());
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.menu_item_color)),
                0, spannableString.length(), 0);
        menuItem.setTitle(spannableString);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.menu_layout,menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.settingId){

            Intent intent = new Intent(getApplicationContext(), UserDashboard.class);
            intent.putExtra("profile","Farhad Foysal\n+8801585855075");
            startActivity(intent);

            return true;
        } else if (item.getItemId()==android.R.id.home) {

            onBackPressed();

            return true;
        }else if (item.getItemId()==R.id.profileId) {

            Intent intent = new Intent(getApplicationContext(), SchoolPanel.class);
//            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            intent.putExtra("profile","Farhad Foysal\n+8801585855075");
            startActivity(intent);

            return true;
        }else if (item.getItemId()==R.id.fsettingId) {

            Intent intent = new Intent(getApplicationContext(), MainPanelActivity.class);
            intent.putExtra("profile","Farhad Foysal\n+8801585855075");
            startActivity(intent);

            return true;
        }else if (item.getItemId()==R.id.flogoutId) {

            Logout logout = new Logout(getApplicationContext());
            logout.getOut();
            finish();
            return true;
        }else if (item.getItemId()==R.id.allUserId) {

            intent = new Intent(BaseMenu.this,AllUsersActivity.class);
//            intent.putExtra("users","01585855075");
            startActivity(intent);

            return true;
        }else if (item.getItemId()==R.id.aboutId) {

            Intent intent = new Intent(getApplicationContext(), ScanActivity.class);
            intent.putExtra("users","01585855075");
            startActivity(intent);

            return true;
        }else if (item.getItemId()==R.id.setSettings) {

            Intent intent = new Intent(getApplicationContext(), Permission.class);
            intent.putExtra("users","01585855075");
            startActivity(intent);

            return true;
        }else if (item.getItemId()==R.id.wifiWebMenu) {

            Intent intent = new Intent(getApplicationContext(), WifiWeb.class);
            intent.putExtra("users","01585855075");
            startActivity(intent);

            return true;
        }else if (item.getItemId()==R.id.updateAppMenu) {

            Intent intent = new Intent(getApplicationContext(), AppUpdate.class);
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

            Intent intent = new Intent(getApplicationContext(), sendSmss.class);
            intent.putExtra("user","");
            intent.putExtra("userMessages", (Serializable) msg);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
