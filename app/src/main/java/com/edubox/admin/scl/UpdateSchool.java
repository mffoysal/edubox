package com.edubox.admin.scl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.edubox.admin.BaseMenu;
import com.edubox.admin.Connection;
import com.edubox.admin.DatabaseManager;
import com.edubox.admin.Internet;
import com.edubox.admin.Logout;
import com.edubox.admin.Network;
import com.edubox.admin.R;
import com.edubox.admin.User;
import com.edubox.admin.UserDAO;
import com.edubox.admin.login.LoginMainActivity;
import com.edubox.admin.user.UserCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UpdateSchool extends BaseMenu implements View.OnClickListener {

    private DrawerLayout drawerLayout;
    private FloatingActionButton fab;
    private MenuItem item;
    private NavigationView navigationView;
    private Button editButton;

    //common for activity start
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

    private User user;
    private String userPhone, sId;
    private School school;
    private SchoolCallback schoolCallback;
    //common for all activity end

    private EditText sclTitle, sclSId, sclName, sclEmail, sclPhone, sclPass, sclEiin, sclWeb, sclItEmail, sclItp1, sclItp2;
    private EditText sclBalance, sclBank, sclAccount, sclVerification, sclAddress, sclTeacher, sclEmployee, sclUser, sclStudent, sclCourse, sclClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_update_school);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        fab = findViewById(R.id.fab);
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), schoolUpload.class);
//                startActivity(intent);
//            }
//        });

        // Common for all activity Start
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
//            actionbar.setTitle(actionbar.getTitle()+" "+url);
            userPhone = url;
            user = logout.getUser();
            sId=user.getSId();
//            school = logout.getSchool();
//            sId=school.getsId();
        }else {
            user = logout.getUser();
            sId=user.getSId();
//            school = logout.getSchool();
//            sId=school.getsId();
//            actionbar.setTitle(actionbar.getTitle()+" "+logout.getStringPreference("userId"));
        }
        if (intent != null && intent.hasExtra("eduBox")) {
            String url = intent.getStringExtra("eduBox");
            actionbar.setTitle(actionbar.getTitle()+" "+url);
        }
        internet = new Internet(getApplicationContext());
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
        //Common for all activity End

//        sclTitle = findViewById(R.id.scltitleName);
//        sclSId = findViewById(R.id.schoolSId);
//        sclCourse = findViewById(R.id.courseNoId);
//        sclClass = findViewById(R.id.classesNoId);
//        sclStudent = findViewById(R.id.studentsNoId);
//        sclTeacher = findViewById(R.id.scl_teachersText);
//        sclUser = findViewById(R.id.scl_usersText);
//        sclEmployee = findViewById(R.id.scl_emplText);
        sclVerification = findViewById(R.id.sclVerification);
        sclItp1 = findViewById(R.id.sclItp1);
        sclItp2 = findViewById(R.id.sclItp2);
        sclItEmail = findViewById(R.id.sclItEmail);
        sclWeb = findViewById(R.id.sclWeb);
        sclBalance = findViewById(R.id.sclBalance);
        sclBank = findViewById(R.id.sclBank);
        sclAccount = findViewById(R.id.sclAccount);
        sclEiin = findViewById(R.id.sclEiin);
        sclPass = findViewById(R.id.sclPass);
        sclPhone = findViewById(R.id.sclPhone);
        sclEmail = findViewById(R.id.sclEmail);
        sclName = findViewById(R.id.sclName);
        sclAddress = findViewById(R.id.sclAddress);

        editButton = findViewById(R.id.savesclButton);
        editButton.setOnClickListener(this);







    }



    private void handleSchoolNotFound() {
        school = new SchoolDAO(database).getSchoolBySID(user.getSId());
        logout.saveSchool(school);
        setSchoolData(school);
    }

    private void processSchool(School s) {
        school = s;
        logout.saveSchool(s);
        setSchoolData(s);
    }

    private void getSchoolData(User user) {
        if (internet.isInternetConnection()){
            getSchool(user.getSId());
            Log.e("school","us sid from getSchooData "+user.getSId() );
        }else {
            try {
                school = new SchoolDAO(database).getSchoolBySID(user.getSId());
                setSchoolData(school);
                Log.e("school","error: "+user.getSId() );
            }catch (Exception e){
                Log.e("school","error: "+e+" "+user.getSId() );
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


    @Override
    public void onClick(View view) {
        String sclname = sclName.getText().toString();
        String sclemail = sclEmail.getText().toString().trim();
        String sclphone = sclPhone.getText().toString().trim();
        String sclpass = sclPass.getText().toString().trim();
        String scleiin = sclEiin.getText().toString().trim();
        String scladdress = sclAddress.getText().toString();
        String sclItemail = sclItEmail.getText().toString();
        String sclItphone1 = sclItp1.getText().toString().trim();
        String sclItphone2 = sclItp2.getText().toString().trim();
        String sclweb = sclWeb.getText().toString().trim();
        String sclbal = sclBalance.getText().toString().trim();
        String sclbank = sclBank.getText().toString();
        String sclbankaccount = sclAccount.getText().toString().trim();
        String sclverfication = sclVerification.getText().toString().trim();

        if (view.getId()==R.id.editButton){
//            Intent intent = new Intent(getApplicationContext(), UpdateSchool.class);
//            startActivity(intent);
            school.setsEmail(sclemail);
            school.setsName(sclname);
            school.setsPhone(sclphone);
            school.setsPass(sclpass);
            school.setsItEmail(sclItemail);
            school.setsItp1(sclItphone1);
            school.setsItp2(sclItphone2);
            school.setsAdrs(scladdress);
            school.setsFundsBank(sclbank);
            school.setsFundsAN(sclbankaccount);
            school.setsFundsBal(sclbal);
            school.setsVerification(sclverfication);
            school.setsWeb(sclweb);
            school.setsEiin(scleiin);
            updateSchoolData(school);
        }else if (view.getId()==R.id.savesclButton){
//            Intent intent = new Intent(getApplicationContext(), UpdateSchool.class);
//            startActivity(intent);
            school.setsEmail(sclemail);
            school.setsName(sclname);
            school.setsPhone(sclphone);
            school.setsPass(sclpass);
            school.setsItEmail(sclItemail);
            school.setsItp1(sclItphone1);
            school.setsItp2(sclItphone2);
            school.setsAdrs(scladdress);
            school.setsFundsBank(sclbank);
            school.setsFundsAN(sclbankaccount);
            school.setsFundsBal(sclbal);
            school.setsVerification(sclverfication);
            school.setsWeb(sclweb);
            school.setsEiin(scleiin);
            updateSchoolData(school);
        }
    }

    private void updateSchoolData(School school) {
        SchoolDAO schoolDAO = new SchoolDAO(database);
        if (internet.isInternetConnection()){
                schoolDAO.updateSchoolDataBysId(school);
                Toast.makeText(getApplicationContext(),"School Data Successfully Updated",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),SchoolPanel.class));
                finish();
        }else {
             long r = schoolDAO.updateSchool(school);
            if(r!=-1){
                Toast.makeText(getApplicationContext(),"School Data Successfully Updated",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(),"School Data not Updated",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setSchoolData(School scl) {
//        sclTitle = findViewById(R.id.scltitleName);
//        sclSId = findViewById(R.id.schoolSId);
//        sclCourse = findViewById(R.id.courseNoId);
//        sclClass = findViewById(R.id.classesNoId);
//        sclStudent = findViewById(R.id.studentsNoId);
//        sclTeacher = findViewById(R.id.scl_teachersText);
//        sclUser = findViewById(R.id.scl_usersText);
//        sclEmployee = findViewById(R.id.scl_emplText);
        sclVerification = findViewById(R.id.sclVerification);
        sclItp1 = findViewById(R.id.sclItp1);
        sclItp2 = findViewById(R.id.sclItp2);
        sclItEmail = findViewById(R.id.sclItEmail);
        sclWeb = findViewById(R.id.sclWeb);
        sclBalance = findViewById(R.id.sclBalance);
        sclBank = findViewById(R.id.sclBank);
        sclAccount = findViewById(R.id.sclAccount);
        sclEiin = findViewById(R.id.sclEiin);
        sclPass = findViewById(R.id.sclPass);
        sclPhone = findViewById(R.id.sclPhone);
        sclEmail = findViewById(R.id.sclEmail);
        sclName = findViewById(R.id.sclName);
        sclAddress = findViewById(R.id.sclAddress);

//        sclTitle.setText(""+scl.getsName());
//        sclSId.setText(""+scl.getsId());
//        sclCourse.setText(""+scl.getsCourse());
//        sclClass.setText(""+scl.getsClass());
//        sclStudent.setText(""+scl.getsStudent());
//        sclTeacher.setText(""+scl.getsTeacher());
//        sclUser.setText(""+scl.getsUser());
//        sclEmployee.setText(""+scl.getsEmpl());
        sclVerification.setText(""+scl.getsVerification());
        sclItp1.setText(""+scl.getsItp1());
        sclItp2.setText(""+scl.getsItp2());
        sclItEmail.setText(""+scl.getsItEmail());
        sclWeb.setText(""+scl.getsWeb());
        sclBalance.setText(""+scl.getsFundsBal());
        sclBank.setText(""+scl.getsFundsBank());
        sclAccount.setText(""+scl.getsFundsAN());
        sclEiin.setText(""+scl.getsEiin());
        sclPass.setText(""+scl.getsPass());
        sclPhone.setText(""+scl.getsPhone());
        sclEmail.setText(scl.getsEmail());
        sclName.setText(""+scl.getsName());
        sclAddress.setText(""+scl.getsAdrs());

    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(Connection.ACTION_CONNECTIVITY_CHANGE);
        registerReceiver(connectivityReceiver,intentFilter);
        Network network = new Network();
        network.onReceive(getApplicationContext(),null);

        Query query = schoolRef.orderByChild("sId").equalTo(sId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    School scl = userSnapshot.getValue(School.class);
                    if (scl != null) {
//                        Log.e("UserFound","User FF Found");
                        school = scl;
                        logout.saveSchool(scl);
                        schoolCallback.onSchoolRetrieved(scl);
                        setSchoolData(scl);
                        return;
                    }
                }
                schoolCallback.onSchoolNotFound();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
//        Toast.makeText(this,"onResume",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(connectivityReceiver);

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



}