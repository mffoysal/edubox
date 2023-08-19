package com.edubox.admin;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.edubox.admin.scanner.ScanActivity;
import com.edubox.admin.scl.SchoolPanel;
import com.edubox.admin.settings.Permission;
import com.edubox.admin.sms.NotificationHelper;
import com.edubox.admin.sms.sendSmss;
import com.edubox.admin.web.WifiWeb;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserDashboard extends BaseMenu implements View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private EditText editText1, editText2;
    private Button bt1,bt2;
    private TextView textView1,textView2;

    private Context context;
    private User user;
    private String userId;
    private SQLiteDatabase database;
    private Intent intent;
    private ActionBar actionbar;
    private Logout logout;
    private BroadcastReceiver connectivityReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        connectivityReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction() != null && intent.getAction().equals(Network.ACTION_CONNECTIVITY_CHANGE)) {
                    boolean isConnected = intent.getBooleanExtra(Network.EXTRA_CONNECTIVITY_STATUS, false);
                    if (isConnected) {
//                        Toast.makeText(getApplicationContext(),"Internet Connected",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();

                    }
                }
            }
        };

        logout = new Logout(getApplicationContext());
        if (!logout.isLoggedIn()){
            Intent intent = new Intent(UserDashboard.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        intent = getIntent();
        actionbar = getSupportActionBar();
        if (intent != null && intent.hasExtra("user")) {
            String url = intent.getStringExtra("user");
            actionbar.setTitle(actionbar.getTitle()+" "+url);
        }else {
//            logout.setLoggedUser("015858550755","01816444372");
            actionbar.setTitle(actionbar.getTitle()+" U: "+logout.getStringPreference("userId"));
        }

        // Remove the Title Bar
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DatabaseManager databaseManager = new DatabaseManager(this);
        databaseManager.openDatabase();
        database = databaseManager.getDatabase();
//        UserDAO userDAO = new UserDAO(database);
//        userDAO.insertUser(user);
//        List<User> userList = userDAO.getAllUsers();
//        int rowsAffected = userDAO.updateUser(user);
//        int rowsDeleted = userDAO.deleteUser(userId);
//        databaseManager.closeDatabase();

        tabLayout = findViewById(R.id.tabLayId);
        viewPager = findViewById(R.id.viewPagerId);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.setAdapter(new myPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);




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

            Intent intent = new Intent(UserDashboard.this, SchoolPanel.class);
//            Intent intent = new Intent(UserDashboard.this,ProfileActivity.class);
            intent.putExtra("profile","Farhad Foysal\n+8801585855075");
            startActivity(intent);

            return true;
        }else if (item.getItemId()==R.id.allUserId) {

            Intent intent = new Intent(UserDashboard.this,AllUsersActivity.class);
            intent.putExtra("users","01585855075");
            startActivity(intent);

            return true;
        }else if (item.getItemId()==R.id.aboutId) {

            Intent intent = new Intent(UserDashboard.this, ScanActivity.class);
            intent.putExtra("users","01585855075");
            startActivity(intent);

            return true;
        }else if (item.getItemId()==R.id.menuId) {
            NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext(),"FarhadFoysal");
            notificationHelper.showBigTextNotification("Edubox","Hello farhad foysal","Farid Ahmed\nRojina Akter\nSanjida Farid Najifa","FarhadFoysal");
            return true;
        }else if (item.getItemId()==R.id.setSettings) {

            Intent intent = new Intent(getApplicationContext(), Permission.class);
            intent.putExtra("users","01585855075");
            startActivity(intent);

            return true;
        }else if (item.getItemId()==R.id.wifiWebMenuId) {

            Intent intent = new Intent(getApplicationContext(), WifiWeb.class);
            intent.putExtra("users","01585855075");
            startActivity(intent);

            return true;
        }else if (item.getItemId()==R.id.shareId) {
            Map<String, String> msg = new HashMap<>();
            msg.put("01585855075","Hello Farhad Foysal");

            Intent intent = new Intent(UserDashboard.this, sendSmss.class);
            intent.putExtra("user","");
            intent.putExtra("userMessages", (Serializable) msg);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
//        Toast.makeText(this,"onCreate Two",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(Connection.ACTION_CONNECTIVITY_CHANGE);
        registerReceiver(connectivityReceiver,intentFilter);
        Network network = new Network();
        network.onReceive(getApplicationContext(),null);
//        Toast.makeText(this,"onStart",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Toast.makeText(this,"onResume",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Toast.makeText(this,"onPause",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(connectivityReceiver);
//        Toast.makeText(this,"onStop",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        Toast.makeText(this,"onRestart",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Toast.makeText(this,"onDestroy",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }




    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.clearButtonId){
            Toast.makeText(this,"Clear Button is Clicked",Toast.LENGTH_SHORT).show();
        } else if (view.getId()==R.id.submitButtonId) {
            Toast.makeText(this,"Submit Button is Clicked",Toast.LENGTH_SHORT).show();
        }
    }

    class myPagerAdapter extends FragmentPagerAdapter{

        String[] text = {"eduBox","MENU","eduWeb"};

        public myPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {

            if(position==0){
                return new oneFrag();
            } else if (position==1) {
                return new twoFrag();
            } else if (position==2) {
                return new threeFrag();
            }

            return null;
        }

        @Override
        public int getCount() {
            return text.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return text[position];
        }
    }
}