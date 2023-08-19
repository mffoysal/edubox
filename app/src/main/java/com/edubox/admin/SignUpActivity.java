package com.edubox.admin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.edubox.admin.scl.School;
import com.edubox.admin.scl.SchoolDAO;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textView1,textView2;
    private EditText editText1,editText2,editText3,editText4,editText5;
    private CheckBox ch;
    private Button button1,button2;
    private CheckBox checkBox1,checkBox2;
    private SQLiteDatabase database;
    private UserDAO userDAO;
    private DatabaseManager databaseManager;
    private Logout logout;
    private BroadcastReceiver connectivityReceiver;
    private FirebaseAuth mAuth;
    private Internet internet;
    private String sId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        internet = new Internet(getApplicationContext());
        connectivityReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction() != null && intent.getAction().equals(Network.ACTION_CONNECTIVITY_CHANGE)) {
                    boolean isConnected = intent.getBooleanExtra(Network.EXTRA_CONNECTIVITY_STATUS, false);
                    if (isConnected) {

//                        Toast.makeText(getApplicationContext(),"Internet Connected"+new Admin().uId()+"  "+new Admin().userId(),Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();

                    }
                }
            }
        };

        databaseManager = new DatabaseManager(this);
        databaseManager.openDatabase();
        database = databaseManager.getDatabase();
//        UserDAO userDAO = new UserDAO(database);
//        userDAO.insertUser(user);
//        List<User> userList = userDAO.getAllUsers();
//        int rowsAffected = userDAO.updateUser(user);
//        int rowsDeleted = userDAO.deleteUser(userId);
//        databaseManager.closeDatabase();
        logout = new Logout(getApplicationContext());

        textView1 = findViewById(R.id.title_signup_dis);
        editText1 = findViewById(R.id.phone_signup);
        editText2 = findViewById(R.id.password_signup);
        editText3 = findViewById(R.id.sclName_signup);
        editText4 = findViewById(R.id.sclEmail_signup);
        button1 = findViewById(R.id.button_signup);
        button2 = findViewById(R.id.button_loginn);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        sId = new Admin().uId();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        String a = editText1.getText().toString();
        String b = editText2.getText().toString();
        String c = editText3.getText().toString();
        String d = editText4.getText().toString();
        if (view.getId()==R.id.button_loginn){
            Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
            intent.putExtra("users",a);
            startActivity(intent);
            finish();
        }else if (view.getId()==R.id.button_signup){
            int re;
            userDAO = new UserDAO(database);
            re=userDAO.checkUser(a,b);

            if (re==0){

                goForCreate(a,b);

            }else {
                Toast.makeText(getApplicationContext(),"User Already Created!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void goForCreate(String a, String b) {
        if(a.length() != 0 && b.length()!=0){

            User user = new User();
            user.setU_type(1);
            user.setPhone(a);
            user.setId(new Admin().uniqueId());
            user.setPass(b);
            user.setSId(sId);
            UserDAO userDAO = new UserDAO(database);
            int r=0;
            r = userDAO.checkUser(user.getPhone());
            if(r!=0){
                Toast.makeText(this,"User already created! Try again!  ",Toast.LENGTH_SHORT).show();
                Log.e("UserFound","User already have with this number");
            }
            long rs = userDAO.insertUser(user);

            if(rs==-1){
                Toast.makeText(this,"Data Not inserted! Try again!  "+rs+"  ",Toast.LENGTH_SHORT).show();

            }else if(rs==-3){
                Toast.makeText(this,"User already created! Try again!  "+rs+"  ",Toast.LENGTH_SHORT).show();

            }else if(rs==-2){
                Toast.makeText(this,"Error occured! Try again!  "+rs+"  ",Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this,"Data Successfully Inserted, Thanks!",Toast.LENGTH_SHORT).show();

                signUP();
                logout.setLoggedIn(true);
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                intent.putExtra("user",a);
                logout.setLoggedUser(a,a);
                logout.saveUser(userDAO.getUser(a));
                startActivity(intent);
                finish();

            }

        }else {
            Toast.makeText(this,"All Fields required!",Toast.LENGTH_SHORT).show();
        }

    }

    private void signUP() {
        String a = editText1.getText().toString();
        String b = editText2.getText().toString();
        String c = editText3.getText().toString();
        String d = editText4.getText().toString();
        internet = new Internet(getApplicationContext());
        if (internet.isInternetConnection()){
            createFirebaseUserWithRealtime(a, b, c, d);
//            new UserDAO(database).saveUserInformationWithRealtime(a,b,c,d);
        }else {
            createSqlSchool(a, b, c, d, 0);
        }
    }

    private void createSqlSchool(String a, String b, String c, String d, int syncStatus) {

        School user = new School();
        user.setSync_status(syncStatus);
        user.setsPhone(a);
        user.setsPass(b);
        user.setId(new Admin().uniqueId());
        user.setsName(c);
        user.setsEmail(d);

        SchoolDAO userDAO = new SchoolDAO(database);
        int r=0;
        r = userDAO.checkSchool(user.getsPhone());
        if(r!=0){
            Toast.makeText(this,"School already created! Try again!  ",Toast.LENGTH_SHORT).show();
            Log.e("UserFound","School already have with this number");
        }
        long rs = userDAO.insertSchool(user);

        if(rs==-1){
            Toast.makeText(this,"Data Not inserted! Try again!  "+rs+"  ",Toast.LENGTH_SHORT).show();

        }else if(rs==-3){
            Toast.makeText(this,"User already created! Try again!  "+rs+"  ",Toast.LENGTH_SHORT).show();

        }else if(rs==-2){
            Toast.makeText(this,"Error occurred! Try again!  "+rs+"  ",Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this,"School Data Successfully Inserted, Thanks!",Toast.LENGTH_SHORT).show();


        }

    }

    private void createFirebaseUser(String phoneNumber, String password, String sclName, String sclEmail) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.createUserWithEmailAndPassword(sclEmail, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // User created successfully
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            // Save additional user information (student ID) in Firestore or Realtime Database
                            //            new UserDAO(database).saveUserInformationWithRealtime(a,b,c,d);
                            saveSchoolInformation(user.getUid(), phoneNumber, password, sclName, sclEmail);

                        } else {
                            // User creation failed
                            Toast.makeText(getApplicationContext(), "Failed to create user: User is null", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // User creation failed
                        Toast.makeText(getApplicationContext(), "Failed to create user: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle any potential exceptions during user creation
                    Toast.makeText(getApplicationContext(), "Failed to create user: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void createFirebaseUserWithRealtime(String phoneNumber, String password, String sclName, String sclEmail) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.createUserWithEmailAndPassword(sclEmail, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // User created successfully
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            // Save additional user information (student ID) in Firestore or Realtime Database
                            School school = new School();
                            school.setsId(sId);
                            school.setId(new Admin().uniqueId());
                            school.setsPhone(phoneNumber);
                            school.setsPass(password);
                            school.setsName(sclName);
                            school.setsEmail(sclEmail);
                            saveSchoolInformationWithRealtime(user.getUid(), school);
                            User user1 = new User();
                            user1.setUserId(user.getUid());
                            user1.setSId(sId);
                            user1.setPhone(phoneNumber);
                            user1.setPass(password);
                            user1.setEmail(sclEmail);
                            new UserDAO(database).saveUserInformationWithRealtime(user.getUid(),user1);
                        } else {
                            // User creation failed
                            Toast.makeText(getApplicationContext(), "Failed to create user: User is null", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // User creation failed
                        Toast.makeText(getApplicationContext(), "Failed to create user: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle any potential exceptions during user creation
                    Toast.makeText(getApplicationContext(), "Failed to create user: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }


    public void saveSchoolInformationWithRealtime(String userId, School s) {
        // Save additional user information in Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = firebaseDatabase.getReference("school");

        School user = new School();
        user = s;
        user.setId(new Admin().uniqueId());
        user.setSync_key(userId);
        user.setSync_status(1);

        usersRef.child(userId)
                .setValue(user)
                .addOnSuccessListener(aVoid -> {
                    // User information saved successfully
                    Toast.makeText(getApplicationContext(), "School User created successfully", Toast.LENGTH_SHORT).show();
//                    finish(); // Finish sign-up activity and return to login screen
                })
                .addOnFailureListener(e -> {
                    // Failed to save user information
                    Toast.makeText(getApplicationContext(), "Failed to save user information: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void saveSchoolInformation(String userId, String phoneNumber, String password, String sclName, String sclEmail) {
        // Save additional user information in Firestore or Realtime Database
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersCollection = db.collection("school");

        Map<String, Object> user = new HashMap<>();
        user.put("sync_key", userId);
        user.put("id", new Admin().uniqueId());
        user.put("sync_status", 1);
        user.put("sPhone", phoneNumber);
        user.put("sPass", password);
        user.put("sName", sclName);
        user.put("sEmail", sclEmail);
        user.put("sId", sId);
        user.put("sLogo","");
        user.put("sAdrs","");
        user.put("sEiin","");
        user.put("sItp1","");
        user.put("sItp2","");
        user.put("sItEmail","");
        user.put("sWeb","");
        user.put("sFundsBal","");
        user.put("sFundsBank","");
        user.put("sFundsAN","");
        user.put("sActivate",0);
        user.put("sVerification","");
        user.put("sStudent",0);
        user.put("sTeacher",0);
        user.put("sClass",0);
        user.put("sUser",0);
        user.put("sSec",0);
        user.put("sCourse",0);
        user.put("sEmpl",0);

        usersCollection.document(userId)
                .set(user)
                .addOnSuccessListener(aVoid -> {
                    // User information saved successfully
                    Toast.makeText(getApplicationContext(), "School User created successfully", Toast.LENGTH_SHORT).show();
//                    finish(); // Finish sign-up activity and return to login screen
                })
                .addOnFailureListener(e -> {
                    // Failed to save user information
                    Toast.makeText(getApplicationContext(), "Failed to save user information: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
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

}