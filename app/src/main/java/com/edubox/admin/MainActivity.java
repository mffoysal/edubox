package com.edubox.admin;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
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

import com.edubox.admin.scl.SchoolPanel;
import com.edubox.admin.view.ViewOne;

import java.util.List;

public class MainActivity extends BaseMenu implements View.OnClickListener {

    private Context context;
    private User user;
    private String userId;
    private View tabLayout;
    private View viewPager;

    private SQLiteDatabase database;

    private EditText stdidt,sIdt,namet,emailt,phonet,passwordt;
    private Button submitb,clearb,deleteb,updateb;
    private TextView textView1, textView2;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove the Title Bar
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

//        Toast.makeText(this,"onCreate",Toast.LENGTH_SHORT).show();

        DatabaseManager databaseManager = new DatabaseManager(this);
        databaseManager.openDatabase();
        database = databaseManager.getDatabase();
//        UserDAO userDAO = new UserDAO(database);
//        userDAO.insertUser(user);
//        List<User> userList = userDAO.getAllUsers();
//        int rowsAffected = userDAO.updateUser(user);
//        int rowsDeleted = userDAO.deleteUser(userId);
//        databaseManager.closeDatabase();


        sIdt = (EditText) findViewById(R.id.sId);
        stdidt = (EditText) findViewById(R.id.stdId);
        namet = (EditText) findViewById(R.id.name);
        emailt = (EditText) findViewById(R.id.email);
        phonet = (EditText) findViewById(R.id.phone);
        passwordt = (EditText) findViewById(R.id.password);

        stdidt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){

                }else {

                }
            }
        });

        stdidt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {



                return false;
            }
        });

        phonet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = charSequence.toString();
                UserDAO userDAO = new UserDAO(database);
                int r = userDAO.checkUser(s);
                if(r!=0){
                    User usera1 = userDAO.getUser(s);
                    Toast.makeText(getApplicationContext(),"User Found "+usera1.getStdName(),Toast.LENGTH_LONG).show();

                    String a = usera1.getSId();
                    String b = usera1.getStdId();
                    String c = usera1.getStdName();
                    String d = usera1.getEmail();
                    String e = usera1.getPass();

                    sIdt.setText(a);
                    stdidt.setText(b);
                    namet.setText(c);
                    emailt.setText(d);
                    passwordt.setText(e);

//                    Toast.makeText(getApplicationContext(),"Please Remove one space from eachEditText - "+usera1.getStdName(),Toast.LENGTH_LONG).show();


                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        stdidt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = charSequence.toString();
                UserDAO userDAO = new UserDAO(database);
                int r = userDAO.checkUser(s);
                if(r!=0){

                    User usera1 = userDAO.getUser(s);
                    Toast.makeText(getApplicationContext(),"User Found "+usera1.getStdName(),Toast.LENGTH_LONG).show();

                    String a = usera1.getSId();
                    String b = usera1.getPhone();
                    String c = usera1.getStdName();
                    String d = usera1.getEmail();
                    String e = usera1.getPass();

                    sIdt.setText(a);
//                    phonet.setText(b);
                    namet.setText(c);
                    emailt.setText(d);
                    passwordt.setText(e);
//                    Toast.makeText(getApplicationContext(),"Please Remove one space from eachEditText - "+usera1.getStdName(),Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        submitb = findViewById(R.id.submitButtonId);
        clearb = findViewById(R.id.clearButtonId);
        deleteb = findViewById(R.id.deleteButtonId);
        updateb = findViewById(R.id.updateButtonId);

        submitb.setOnClickListener(this);
        clearb.setOnClickListener(this);
        deleteb.setOnClickListener(this);
        updateb.setOnClickListener(this);

//        tabLayout = findViewById(R.id.tabLayId);
//        viewPager = findViewById(R.id.viewPagerId);
//
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//
//        viewPager.setAdapter(new myPagerAdapter(getSupportFragmentManager()));
//        tabLayout.setupWithViewPager(viewPager);

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

            Intent intent = new Intent(MainActivity.this, SchoolPanel.class);
//            Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
            intent.putExtra("profile","Farhad Foysal\n+8801585855075");
            startActivity(intent);

            return true;
        }else if (item.getItemId()==R.id.allUserId) {

            Intent intent = new Intent(MainActivity.this,AllUsersActivity.class);
            intent.putExtra("users","01585855075");
            startActivity(intent);

            return true;
        }else if (item.getItemId()==R.id.aboutId) {
            return true;
        }else if (item.getItemId()==R.id.menuId) {
            return true;
        }else if (item.getItemId()==R.id.shareId) {
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

        String sId = sIdt.getText().toString();
        String stdId = stdidt.getText().toString();
        String name = namet.getText().toString();
        String email = emailt.getText().toString();
        String phone = phonet.getText().toString();
        String password = passwordt.getText().toString();

        if(view.getId()==R.id.clearButtonId){
            stdidt.setText("");
            sIdt.setText("");
            namet.setText("");
            emailt.setText("");
            phonet.setText("");
            passwordt.setText("");
            Intent intent = new Intent(this,ScannerActivity.class);
            startActivity(intent);
            Toast.makeText(this,"Clear Button is Clicked",Toast.LENGTH_SHORT).show();
        } else if (view.getId()==R.id.submitButtonId) {
            if(stdId.length() != 0 && sId.length()!=0 && name.length()!=0 && email.length()!=0 && phone.length()!=0 && password.length()!=0){

                User user = new User();
                user.setSId(sId);
                user.setStdId(stdId);
                user.setEmail(email);
                user.setU_type(1);
                user.setPhone(phone);
                user.setPass(password);
                user.setStdName(name);

                UserDAO userDAO = new UserDAO(database);
                int r=0;
                r = userDAO.checkUser(user.getPhone());
                if(r!=0){
                        Toast.makeText(this,"User already created! Try again!  "+name+email+phone+password+sId,Toast.LENGTH_SHORT).show();
                        Log.e("UserFound","User already have with this number");
                }
                long rs = userDAO.insertUser(user);

                if(rs==-1){
                    Toast.makeText(this,"Data Not inserted! Try again!  "+rs+"  "+name+email+phone+password+sId,Toast.LENGTH_SHORT).show();

                }else if(rs==-3){
                    Toast.makeText(this,"User already created! Try again!  "+rs+"  "+name+email+phone+password+sId,Toast.LENGTH_SHORT).show();

                }else if(rs==-2){
                    Toast.makeText(this,"Error occured! Try again!  "+rs+"  "+name+email+phone+password+sId,Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this,"Data Successfully Inserted, Thanks!",Toast.LENGTH_SHORT).show();

                }

            }else {
                Toast.makeText(this,"All Fields required!",Toast.LENGTH_SHORT).show();
            }
//            Toast.makeText(this,"Submit Button is Clicked",Toast.LENGTH_SHORT).show();
        } else if (view.getId()==R.id.deleteButtonId) {
            String s = phonet.getText().toString();
            UserDAO userDAO = new UserDAO(database);
            int r = userDAO.checkUser(s);
            if(r!=0){

                int rr = userDAO.deleteUser(s);
                if (rr!=-1){
                    Toast.makeText(getApplicationContext(),"User Deleted Successfully - ",Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(getApplicationContext(),"Try Again - ",Toast.LENGTH_LONG).show();

                }
                sIdt.setText("");
                stdidt.setText("");
                namet.setText("");
                emailt.setText("");
                passwordt.setText("");



            }

            Toast.makeText(this,"DELETE Button is Clicked",Toast.LENGTH_SHORT).show();
        } else if (view.getId()==R.id.updateButtonId) {
            String s = phonet.getText().toString();
            UserDAO userDAO = new UserDAO(database);
            int r = userDAO.checkUser(s);
            if(r!=0){
                User user = new User();
                user.setSId(sId);
                user.setStdId(stdId);
                user.setEmail(email);
                user.setU_type(1);
                user.setPhone(phone);
                user.setPass(password);
                user.setStdName(name);

                long rr = userDAO.updateUser(user);
                if (rr!=-1){
                    Toast.makeText(getApplicationContext(),"User Updated Successfully - ",Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(getApplicationContext(),"Try Again - ",Toast.LENGTH_LONG).show();

                }

                Toast.makeText(getApplicationContext(),"Please Remove one space from eachEditText - ",Toast.LENGTH_LONG).show();


            }

            Toast.makeText(this,"UPDATE Button is Clicked",Toast.LENGTH_SHORT).show();
        }
    }

    private void goToPage() {
        intent = getIntent();
        Uri data = intent.getData();
        if (data != null) {
            List<String> pathSegments = data.getPathSegments();
            if (pathSegments.size() >= 3) { // Assuming at least three segments in the URL
                String value1 = pathSegments.get(1);
                String value2 = pathSegments.get(2);

                // Now you have the extracted values, you can use them as needed
                // For example, display them in TextViews or perform other actions

                // Example: Display values in TextViews
//                TextView textView1 = findViewById(R.id.textViewValue1);
//                TextView textView2 = findViewById(R.id.textViewValue2);
//                textView1.setText(value1);
//                textView2.setText(value2);
            } else if (pathSegments.size() >= 2) {

            }else if (pathSegments.size() >= 1) {

            }else if (pathSegments.size() >= 4) {

            }else if (pathSegments.size() >= 5) {

            }else if (pathSegments.size() >= 6) {

            }else if (pathSegments.size() >= 7) {

            }else {
                intent = new Intent(getApplicationContext(), ViewOne.class);
                startActivity(intent);
                finish();
            }
        }else {
            intent = new Intent(getApplicationContext(), ViewOne.class);
            startActivity(intent);
            finish();
        }
    }

}