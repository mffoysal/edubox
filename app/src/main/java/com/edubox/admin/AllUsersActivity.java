package com.edubox.admin;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AllUsersActivity extends BaseMenu {
    private Context context;
    private User user;
    private String userId;
    private View tabLayout;
    private View viewPager;

    private SQLiteDatabase database;
    private ListView listView;
    private List<User> users;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("users")) {
            String url = intent.getStringExtra("users");
//            webView.loadUrl(url);
//            textView.setText(url);
        }
        users = new ArrayList<>();

        DatabaseManager databaseManager = new DatabaseManager(this);
        databaseManager.openDatabase();
        database = databaseManager.getDatabase();

        listAdapter = new listAdapter(AllUsersActivity.this,users);
        listView = findViewById(R.id.allUsersListId);
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

            Intent intent = new Intent(AllUsersActivity.this,ProfileActivity.class);
            intent.putExtra("profile","Farhad Foysal\n+8801585855075");
            startActivity(intent);

            return true;
        }else if (item.getItemId()==R.id.allUserId) {

            Intent intent = new Intent(AllUsersActivity.this,MainActivity.class);
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
        users.clear();
//        users = new UserDAO(database).getAllUsers();
        users.addAll(new UserDAO(database).getAllUsers());
        listView.setAdapter(listAdapter);

//        Toast.makeText(this,"onStartAll Users",Toast.LENGTH_SHORT).show();
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

}