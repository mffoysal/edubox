package com.edubox.admin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.edubox.admin.main.UserPanel;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends BaseMenu implements View.OnClickListener {

    private TextView textView1,textView2;
    private EditText editText1,editText2;
    private Button button1,button2;
    private CheckBox checkBox1,checkBox2;
    private Logout logout;
    private SQLiteDatabase database;
    private DatabaseManager databaseManager;
    private UserDAO userDAO;
    private Internet internet;
    private FirebaseAuth mAuth;
    private BroadcastReceiver connectivityReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        logout = new Logout(getApplicationContext());
        if (logout.isLoggedIn()){
            Intent intent = new Intent(LoginActivity.this,UserPanel.class);
            startActivity(intent);
            finish();
        }

        connectivityReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction() != null && intent.getAction().equals(Network.ACTION_CONNECTIVITY_CHANGE)) {
                    boolean isConnected = intent.getBooleanExtra(Network.EXTRA_CONNECTIVITY_STATUS, false);
                    if (isConnected) {
                        Toast.makeText(getApplicationContext(),"Internet Connected",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();

                    }
                }
            }
        };
// INTERNET CONNECTION CHECK METHOD TWO
        internet = new Internet(getApplicationContext());
        if (internet.isInternetConnection()){
            Toast.makeText(this,"You are connected with Internet Connection",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"You are not connected with Internet Connection",Toast.LENGTH_SHORT).show();
        }

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

        textView1 = findViewById(R.id.title_login_dis);
        editText1 = findViewById(R.id.phone_login);
        editText2 = findViewById(R.id.password_login);
        button1 = findViewById(R.id.button_login);
        button2 = findViewById(R.id.button_signupp);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        

//        logout.setLoggedIn(true);
//        Intent intent = new Intent(LoginActivity.this,UserDashboard.class);
//        startActivity(intent);
//        finish();
    }

    @Override
    public void onClick(View view) {
        String a = editText1.getText().toString();
        String b = editText2.getText().toString();
        if (view.getId()==R.id.button_signupp){
            Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
            intent.putExtra("users","01585855075");
            startActivity(intent);
            finish();
        } else if (view.getId()==R.id.button_login){
            int r;
            userDAO = new UserDAO(database);
            r=userDAO.checkUser(a,b);
            if (r!=0){
                logout.setLoggedIn(true);
                Intent intent = new Intent(LoginActivity.this, UserPanel.class);
                intent.putExtra("user",a);
                startActivity(intent);
                logout.setLoggedUser(a,a);
                logout.saveUser(userDAO.getUser(a));
                finish();
            }else {
                Toast.makeText(getApplicationContext(),"User not Found",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(Connection.ACTION_CONNECTIVITY_CHANGE);
        registerReceiver(connectivityReceiver,intentFilter);
        Network network = new Network();
        network.onReceive(getApplicationContext(),null);
//        Toast.makeText(this,"onStart"
//        ,Toast.LENGTH_SHORT).show();
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