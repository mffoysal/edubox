package com.edubox.admin.main;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.edubox.admin.AllUsersActivity;
import com.edubox.admin.DatabaseManager;
import com.edubox.admin.Internet;
import com.edubox.admin.Logout;
import com.edubox.admin.MainPanelActivity;
import com.edubox.admin.R;
import com.edubox.admin.User;
import com.edubox.admin.UserDAO;
import com.edubox.admin.UserDashboard;
import com.edubox.admin.databinding.ActivityUserPanelBinding;
import com.edubox.admin.fragment.addFrag;
import com.edubox.admin.fragment.docsFrag;
import com.edubox.admin.fragment.homeFrag;
import com.edubox.admin.fragment.pdfFrag;
import com.edubox.admin.fragment.phoneFrag;
import com.edubox.admin.login.LoginMainActivity;
import com.edubox.admin.major.AllDepartment;
import com.edubox.admin.scanner.ScanActivity;
import com.edubox.admin.scl.School;
import com.edubox.admin.scl.SchoolCallback;
import com.edubox.admin.scl.SchoolDAO;
import com.edubox.admin.scl.SchoolPanel;
import com.edubox.admin.session.AllSession;
import com.edubox.admin.settings.Permission;
import com.edubox.admin.sms.NotificationHelper;
import com.edubox.admin.sms.sendSmss;
import com.edubox.admin.update.AppUpdate;
import com.edubox.admin.user.UserCallback;
import com.edubox.admin.web.WifiWeb;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserPanel extends AppCompatActivity implements View.OnClickListener {

    private Logout logout;
    private SQLiteDatabase database;
    private DatabaseManager databaseManager;
    private UserDAO userDAO;
    private Internet internet;
    private FirebaseAuth mAuth;
    private FirebaseDatabase fdatabase = FirebaseDatabase.getInstance();
    private DatabaseReference reference = fdatabase.getReference("users");

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference schoolRef = firebaseDatabase.getReference("school");

    private DatabaseReference usersRef;
    private UserCallback userCallback;
    private BroadcastReceiver connectivityReceiver;
    private Intent intent;
    private ActionBar actionbar;
    private ActivityUserPanelBinding binding;
    private User user;
    private String userPhone, sId;
    private School school;
    private SchoolCallback schoolCallback;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserPanelBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_user_panel);
        setContentView(binding.getRoot());

//        Toolbar toolbar = findViewById(R.id.toolbar); //Ignore red line errors
//        setSupportActionBar(toolbar);

        replaceFragment(new homeFrag());
        binding.userPanelNavViewId.setBackground(null);
        binding.userPanelNavViewId.setOnItemSelectedListener(item -> {
            if(item.getItemId()==R.id.menu_homeId){
                replaceFragment(new homeFrag());
            } else if (item.getItemId()==R.id.menu_addId) {
                replaceFragment(new addFrag());
            } else if (item.getItemId()==R.id.menu_callId) {
                replaceFragment(new phoneFrag());
            } else if (item.getItemId()==R.id.menu_pdfId) {
                replaceFragment(new pdfFrag());
            } else if (item.getItemId()==R.id.menu_documentId) {
                replaceFragment(new docsFrag());
            } else if (item.getItemId()==R.id.navAddId) {

            }
            return false;
        });

        databaseManager = new DatabaseManager(this);
        databaseManager.openDatabase();
        database = databaseManager.getDatabase();

        logout = new Logout(this);
        if (!logout.isLoggedIn()){
            Intent intent = new Intent(getApplicationContext(), LoginMainActivity.class);
            startActivity(intent);
            finish();
        }
        intent = getIntent();
        actionbar = getSupportActionBar();
        if (intent != null && intent.hasExtra("user")) {
            String url = intent.getStringExtra("user");
            actionbar.setTitle(actionbar.getTitle()+" "+url);
            userPhone = url;
            user = logout.getUser();
            sId=user.getSId();
        }else {
            user = logout.getUser();
            sId=user.getSId();
            actionbar.setTitle(actionbar.getTitle()+" "+logout.getStringPreference("userId"));
        }
        if (intent != null && intent.hasExtra("eduBox")) {
            String url = intent.getStringExtra("eduBox");
            actionbar.setTitle(actionbar.getTitle()+" "+url);
        }
        internet = new Internet(getApplicationContext());
        getSchoolData(user);
        schoolCallback = new SchoolCallback() {
            @Override
            public void onSchoolRetrieved(School school) {
                processSchool(school);
            }

            @Override
            public void onSchoolNotFound() {
                handleSchoolNotFound();
            }

        };


    }

    private void handleSchoolNotFound() {
        school = new SchoolDAO(database).getSchoolBySID(user.getSId());
    }

    private void processSchool(School s) {
            school = s;
            logout.saveSchool(school);
        Log.e("school","school sId from processSchool "+s.getsId());
    }

    private void getSchoolData(User user) {
        if (internet.isInternetConnection()){
            getSchool(user.getSId());
            Log.e("school","us sid from getSchooData "+user.getSId() );
        }else {
            try {
                school = new SchoolDAO(database).getSchoolBySID(user.getSId());
                logout.saveSchool(school);
                Log.e("school","us "+user.getSId() );
            }catch (Exception e){
                Log.e("school","us "+e+" "+user.getSId() );
            }
        }
    }

    private School getSchool(String getsId) {
        Query query = schoolRef.orderByChild("sId").equalTo(getsId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    School scl = userSnapshot.getValue(School.class);
                    if (scl != null) {
//                        Log.e("UserFound","User FF Found");
                        school = scl;
                        logout.saveSchool(scl);
                        schoolCallback.onSchoolRetrieved(scl);
                        return;
                    }
                }
                schoolCallback.onSchoolNotFound();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return new School();
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.userPanelMainFrameId, fragment);
        fragmentTransaction.commit();
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

            Intent intent = new Intent(getApplicationContext(), UserDashboard.class);
            intent.putExtra("profile","Farhad Foysal\n+8801585855075");
            startActivity(intent);

            return true;
        } else if (item.getItemId()==R.id.profileId) {

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
        }else if (item.getItemId()==R.id.allUserId) {

            Intent intent = new Intent(getApplicationContext(), AllUsersActivity.class);
            intent.putExtra("users","01585855075");
            startActivity(intent);

            return true;
        }else if (item.getItemId()==R.id.aboutId) {

            Intent intent = new Intent(getApplicationContext(), ScanActivity.class);
            intent.putExtra("users","01585855075");
            startActivity(intent);

            return true;
        }else if (item.getItemId()==R.id.depAllId) {

            Intent intent = new Intent(getApplicationContext(), AllDepartment.class);
            startActivity(intent);

            return true;
        }else if (item.getItemId()==R.id.sessionAllId) {

            Intent intent = new Intent(getApplicationContext(), AllSession.class);
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
        }else if (item.getItemId()==R.id.updateAppMenu) {

            Intent intent = new Intent(getApplicationContext(), AppUpdate.class);
            intent.putExtra("users","01585855075");
            startActivity(intent);

            return true;
        }else if (item.getItemId()==R.id.logoutId) {

            Logout logout = new Logout(getApplicationContext());
            logout.getOut();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

    }


}