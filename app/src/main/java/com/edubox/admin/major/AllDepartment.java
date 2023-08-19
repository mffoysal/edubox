package com.edubox.admin.major;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edubox.admin.Admin;
import com.edubox.admin.DatabaseManager;
import com.edubox.admin.Internet;
import com.edubox.admin.Logout;
import com.edubox.admin.Network;
import com.edubox.admin.R;
import com.edubox.admin.User;
import com.edubox.admin.UserDAO;
import com.edubox.admin.adapter.DepartmentAdapter;
import com.edubox.admin.login.LoginMainActivity;
import com.edubox.admin.scl.School;
import com.edubox.admin.scl.SchoolCallback;
import com.edubox.admin.scl.SchoolDAO;
import com.edubox.admin.scl.SchoolPanel;
import com.edubox.admin.scl.schoolDep;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllDepartment extends AppCompatActivity implements View.OnClickListener, DepartmentAdapter.OnItemClickListener, DepartmentAdapter.OnEditClickListener, DepartmentAdapter.OnDeleteClickListener {

    private TextView addDepButton;
    private Dialog dialog;
    private Layout layout;
    private schoolDep schoolDep;
    private schoolDep d;

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
    private DatabaseReference majorRef = firebaseDatabase.getReference("major");

    private DatabaseReference usersRef;
    private UserCallback userCallback;
    private BroadcastReceiver connectivityReceiver;
    private Intent intent;
    private ActionBar actionbar;

    private User user;
    private String userPhone, sId;
    private School school;
    private SchoolCallback schoolCallback;



    private RecyclerView recyclerView;
    private List<schoolDep> schoolDeps;
    private ValueEventListener valueEventListener;
    private DepartmentAdapter departmentAdapter;
    private AlertDialog adialog;
    private SearchView searchView;
    private schoolDep s;



    public AllDepartment() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_all_department);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        databaseManager = new DatabaseManager(this);
        databaseManager.openDatabase();
        database = databaseManager.getDatabase();

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




        recyclerView = findViewById(R.id.departmentRecycler);
        addDepButton = findViewById(R.id.addDep);
        addDepButton.setOnClickListener(this);

        searchView = findViewById(R.id.searchDep);
        searchView.clearFocus();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        AlertDialog.Builder builder = new AlertDialog.Builder(AllDepartment.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        adialog = builder.create();
        adialog.show();
        schoolDeps = new ArrayList<>();

        departmentAdapter = new DepartmentAdapter(getApplicationContext(),schoolDeps, (DepartmentAdapter.OnItemClickListener) this, (DepartmentAdapter.OnEditClickListener) this, (DepartmentAdapter.OnDeleteClickListener) this);
        recyclerView.setAdapter(departmentAdapter);

//        adialog.show();
        fetchDeps();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });
    }

    private void fetchDeps() {
        adialog.show();
        valueEventListener = majorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                schoolDeps.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()) {
                    schoolDep schoolDep1 = itemSnapshot.getValue(schoolDep.class);
                    if (schoolDep1 != null && schoolDep1.getsId().equals(sId)) {
                        schoolDep1.setKey(itemSnapshot.getKey());
                        schoolDeps.add(schoolDep1);
                    }
                }
                departmentAdapter.notifyDataSetChanged();
                adialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                adialog.dismiss();
            }
        });


    }

    private void handleSchoolNotFound() {
        school = new SchoolDAO(database).getSchoolBySID(user.getSId());
        logout.saveSchool(school);

    }

    private void processSchool(School s) {
        school = s;
        logout.saveSchool(s);
    }

    private void getSchoolData(User user) {
        if (internet.isInternetConnection()){
            getSchool(user.getSId());
            Log.e("school","us sid from getSchooData "+user.getSId() );
        }else {
            try {
                school = new SchoolDAO(database).getSchoolBySID(user.getSId());

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
        if (view.getId()==R.id.addDep){
                showBottomDialog();
        }
    }

    private void showBottomDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);

        EditText depName = dialog.findViewById(R.id.depNameId);
        EditText depLocation = dialog.findViewById(R.id.depLocationId);
        EditText depStart = dialog.findViewById(R.id.startId);
        EditText depEnd = dialog.findViewById(R.id.endId);
        EditText depPhone = dialog.findViewById(R.id.depPhoneId);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);
        ImageView cancelButton2 = dialog.findViewById(R.id.cancelButton2);
        Button saveDep = dialog.findViewById(R.id.signUpBtn);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        cancelButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        saveDep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String name = depName.getText().toString();
               String location = depLocation.getText().toString();
               String start = depStart.getText().toString().trim();
               String end = depEnd.getText().toString().trim();
               String phone = depPhone.getText().toString().trim();

                d = new schoolDep();
                d.setid(new Admin().uniqueId());
                d.setsId(""+sId);
                d.setmName(""+name);
                d.setLocation(""+location);
                d.setPhone(""+phone);
                d.setmStatus(1);
                d.setstartId(""+start);
                d.setendId(""+end);
                d.setUniqueId(new Admin().uId());


                if (phone.isEmpty()){
                    depPhone.setError("Phone cannot be empty");
                }
                if (start.isEmpty()){
                    depStart.setError("Email cannot be empty");
                }
                if (name.isEmpty()){
                    depName.setError("Password cannot be empty");
                } else{

                    if (internet.isInternetConnection()){
                        checkDep(d);
                    }else {
                        createDepInLocal(d);
                    }

                }

//                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void createDepInLocal(schoolDep d) {

    }

    private void checkDep(schoolDep d) {
        String userUsername = d.getmName();
        String userPassword = d.getPhone();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("major");
        Query checkUserDatabase = reference.orderByChild("mName").equalTo(userUsername);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
//                        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        // Retrieve the child data using the userSnapshot
                        String phoneFromDB = userSnapshot.child("phone").getValue(String.class);
                        String mName = userSnapshot.child("mName").getValue(String.class);
                        String IdFromDB = userSnapshot.child("id").getValue(String.class);
                        String sIdFromDB = userSnapshot.child("sId").getValue(String.class);
                        String startIdFromDB = userSnapshot.child("startId").getValue(String.class);
                        String endIdFromDB = userSnapshot.child("endId").getValue(String.class);

//                        Toast.makeText(getApplicationContext(), "" + sIdFromDB, Toast.LENGTH_SHORT).show();

                        // Perform actions with the retrieved data
                        if (phoneFromDB.equals(userPassword)) {

                        Toast.makeText(getApplicationContext(), "Department already exist!" + sIdFromDB, Toast.LENGTH_SHORT).show();

                            return; // Exit the loop and method since the user is found
                        }
                    }
                        Toast.makeText(getApplicationContext(), "Department Exist", Toast.LENGTH_SHORT).show();

                } else {
                    saveDepInformationWithRealtime(d);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the cancellation
            }
        });
    }

    private void saveDepInformationWithRealtime(schoolDep d) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference depRef = firebaseDatabase.getReference("major");

        String key = depRef.push().getKey();
        String uniqueId = d.getUniqueId();
        s = new schoolDep();
        s = d;
        s.setSync_key(key);
        s.setSync_status(1);
        schoolDep finalS = s;

        depRef.child(uniqueId)
                .setValue(s)
                .addOnSuccessListener(aVoid -> {
                    // User information saved successfully
                    createDepInLocal(finalS);
                    Toast.makeText(getApplicationContext(), "School Department created successfully", Toast.LENGTH_SHORT).show();
//                    finish(); // Finish sign-up activity and return to login screen
                })
                .addOnFailureListener(e -> {
                    // Failed to save user information
                    Toast.makeText(getApplicationContext(), "Failed to save Department information: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void saveDepData(schoolDep d) {
//        schoolDepDAO schoolDepDAO = new schoolDepDAO(database);
        if (internet.isInternetConnection()){

            Toast.makeText(getApplicationContext(),"Department Data Successfully Updated",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), SchoolPanel.class));
            finish();
        }else {

        }
    }

    public void showBottomSheet(int layout){
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    public void searchList(String text){
        ArrayList<schoolDep> searchList = new ArrayList<>();
        for (schoolDep schoolDep1: searchList){
            if (schoolDep1.getmName().toLowerCase().contains(text.toLowerCase())){
                searchList.add(schoolDep1);
            }
        }
        if (searchList.isEmpty()){
            Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
        } else {
            departmentAdapter.searchDepList(searchList);
        }
    }

    private void updateCurrentStudentIdForDepartment(String departmentUniqueId, String newStudentId) {
        DatabaseReference depRef = FirebaseDatabase.getInstance().getReference().child("major").child(departmentUniqueId);

        Map<String, Object> updates = new HashMap<>();
        updates.put("currentId", newStudentId);

        depRef.updateChildren(updates)
                .addOnSuccessListener(aVoid -> {

                    Toast.makeText(getApplicationContext(), "Current Student Id updated for the department.", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {

                    Toast.makeText(getApplicationContext(), "Failed to update Current Student Id: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void getDepartmentDataByUniqueId(String uniqueId) {
        DatabaseReference depRef = FirebaseDatabase.getInstance().getReference().child("major").child(uniqueId);

        depRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    schoolDep department = dataSnapshot.getValue(schoolDep.class);
                    if (department != null) {

                        String name = department.getmName();
                        String currentStudentId = department.getcurrentId();

                    }
                } else {

                    Toast.makeText(getApplicationContext(), "Department data not found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Error occurred while retrieving data
                Toast.makeText(getApplicationContext(), "Error retrieving department data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteDepartmentByUniqueId(String uniqueId) {
        DatabaseReference depRef = FirebaseDatabase.getInstance().getReference().child("major").child(uniqueId);

        depRef.removeValue()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getApplicationContext(), "Department data deleted successfully.", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Failed to delete department data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onItemClick(schoolDep schoolDep) {
        Toast.makeText(this, "Found ", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onEditClick(schoolDep schoolDep) {
        Toast.makeText(this, "Found ", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDeleteClick(schoolDep schoolDep) {
        Toast.makeText(this, "Found ", Toast.LENGTH_SHORT).show();

    }
}