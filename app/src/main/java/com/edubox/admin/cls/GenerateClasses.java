package com.edubox.admin.cls;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import com.edubox.admin.Admin;
import com.edubox.admin.BaseMenu;
import com.edubox.admin.DatabaseManager;
import com.edubox.admin.Internet;
import com.edubox.admin.Logout;
import com.edubox.admin.Network;
import com.edubox.admin.R;
import com.edubox.admin.User;
import com.edubox.admin.UserDAO;
import com.edubox.admin.login.LoginMainActivity;
import com.edubox.admin.scl.School;
import com.edubox.admin.scl.SchoolCallback;
import com.edubox.admin.scl.SchoolDAO;
import com.edubox.admin.scl.schoolDep;
import com.edubox.admin.session.Session;
import com.edubox.admin.std.AllStudentPanel;
import com.edubox.admin.user.UserCallback;
import com.google.android.material.textfield.TextInputEditText;
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

public class GenerateClasses extends BaseMenu implements View.OnClickListener {

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
    private DatabaseReference studentRef = firebaseDatabase.getReference("students");
    private DatabaseReference sessionRef = firebaseDatabase.getReference("session");

    private DatabaseReference usersRef;
    private UserCallback userCallback;
    private BroadcastReceiver connectivityReceiver;
    private Intent intent;
    private ActionBar actionbar;

    private User user;
    private String userPhone, sId;
    private School school;
    private SchoolCallback schoolCallback;


    private schoolDep schoolDep;
    private Session session;
    private List<schoolDep> schoolDeps;
    private ArrayAdapter<schoolDep> departmentAdapter;
    private ValueEventListener valueEventListener;
    private List<Session> sessions;
    private ArrayAdapter<Session> sessionAdapter;


    private Map<String, Integer> programs = new HashMap<>();
    private ArrayAdapter<String> programsArrayAdapter;
    private AutoCompleteTextView program, departmentSel, sessionSel;
    private int programDay = 1;
    private Button saveClass;
    private TextInputEditText classesText, sectionsText;
    private Class classData;
    private Class s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_classes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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


        classesText = findViewById(R.id.classesId);
        sectionsText = findViewById(R.id.sectionsId);
        saveClass = findViewById(R.id.saveClassButton);
        saveClass.setOnClickListener(this);
        programs.put("Day",1);
        programs.put("Evening",2);
        program = findViewById(R.id.programId);

        departmentSel = findViewById(R.id.selectDepartmentId);
        sessionSel = findViewById(R.id.sessionAllId);

        String[] pitems = new String[]{"Day","Evening"};
        programsArrayAdapter = new ArrayAdapter<>(this,R.layout.custom_spinner_item,pitems);
        program.setAdapter(programsArrayAdapter);
        program.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) adapterView.getItemAtPosition(i);
                int itemInt = programs.get(selectedItem);
                Toast.makeText(getApplicationContext(),""+itemInt,Toast.LENGTH_SHORT).show();
                Log.e("tag",""+itemInt);
//                program.setText(""+itemInt);
                programDay = itemInt;
            }
        });

        schoolDeps = new ArrayList<>();
        sessions = new ArrayList<>();
        departmentAdapter = new ArrayAdapter<>(this,R.layout.custom_spinner_item,schoolDeps);
        departmentSel.setAdapter(departmentAdapter);
        sessionAdapter = new ArrayAdapter<>(this,R.layout.custom_spinner_item,sessions);
        sessionSel.setAdapter(sessionAdapter);
        sessionSel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    session = (Session) adapterView.getItemAtPosition(i);

            }
        });
        departmentSel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                schoolDep = (schoolDep) adapterView.getItemAtPosition(i);

            }
        });

        if (internet.isInternetConnection()){
            fetchDeps();
            fetchSessions();
        }else {
//            fetchDepsWithLocal();
//            fetchSessionsWithLocal();
        }

        

    }

    private void fetchDeps() {
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void fetchSessions() {
        valueEventListener = sessionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sessions.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()) {
                    Session session = itemSnapshot.getValue(Session.class);
                    if (session != null && session.getSId().equals(sId)) {
                        session.setKey(itemSnapshot.getKey());
                        sessions.add(session);
                    }
                }
                sessionAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        if (view.getId()==R.id.saveClassButton){



                if(sessionSel.getText()!=null) {

                    if(departmentSel.getText()!=null) {

                        if(program.getText()!=null) {

                            if (classesText.getText()!=null){
                                if (sectionsText!=null){

                                    Session sd = new Session();
                                    sd = session;
                                    schoolDep dep = new schoolDep();
                                    dep = schoolDep;
                                    String departmentData = dep.getUniqueId();
                                    String sessionData = sd.getUniqueId();
                                    int sectionData = Integer.parseInt(sectionsText.getText().toString().trim());
                                    int classData = Integer.parseInt(classesText.getText().toString().trim());
                                    int programD = programDay;
                                    int i =1;
                                    if (classData>12){
                                        Toast.makeText(getApplicationContext(),"Please make sure! you will create maximum 12 class for a session",Toast.LENGTH_SHORT).show();
                                    }else {
                                        for (i=1;i<=classData;i++){
                                            Class cls = new Class();
                                            cls.setId(new Admin().uniqueId());
                                            cls.setUniqueId(new Admin().uId());
                                            cls.setSId(sId);
                                            cls.setclsCode(""+i+"-"+sd.getaYname());
                                            cls.setclsName(""+i);
                                            cls.setDepartmentId(departmentData);
                                            cls.setdepId(dep.getid());
                                            cls.setSessionId(sessionData);
                                            cls.setaYearId(sd.getId());
                                            cls.setaYear(sd.getaYname());
                                            cls.setMaxSec(sectionData);
                                            cls.setProgram(programD);
                                            cls.setclsId(new Admin().generateUniqueID());
                                            cls.setaStatus(1);

                                            if (internet.isInternetConnection()){
                                                saveClassOnline(cls);
                                            }else {
                                                saveClassOffline(cls);
                                            }

                                        }
                                    }

                                }else {
                                    Toast.makeText(getApplicationContext(),"Please Enter Sections Number",Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(getApplicationContext(),"Please Enter Classes Number",Toast.LENGTH_SHORT).show();
                            }


                        }else {
                            Toast.makeText(getApplicationContext(),"Please Select Program",Toast.LENGTH_SHORT).show();                        }

                    }else {
                        Toast.makeText(getApplicationContext(),"Please Select Department",Toast.LENGTH_SHORT).show();                    }

                }else {
                    Toast.makeText(getApplicationContext(),"Please Select Session",Toast.LENGTH_SHORT).show();                }





        }
    }

    private void saveClassOnline(Class cls) {
        saveClassInformationWithRealtime(cls);
    }

    private void saveClassOffline(Class cls) {

    }

    private void saveClassInformationWithRealtime(Class d) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference classRef = firebaseDatabase.getReference("class");

        String key = classRef.push().getKey();
        String uniqueId = d.getUniqueId();
        s = new Class();
        s = d;
        s.setSync_key(key);
        s.setSync_status(1);
        Class finalS = s;

        classRef.child(uniqueId)
                .setValue(s)
                .addOnSuccessListener(aVoid -> {
                    // User information saved successfully
                    createClassInLocal(finalS);
                    Toast.makeText(getApplicationContext(), "Class Account created successfully", Toast.LENGTH_SHORT).show();
//                    finish(); // Finish sign-up activity and return to login screen
                })
                .addOnFailureListener(e -> {
                    // Failed to save user information
                    Toast.makeText(getApplicationContext(), "Failed to save Class information: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void createClassInLocal(Class finalS) {

    }

    private void saveClassData(Class d) {
//        studentDAO studentDAO = new studentDAO(database);
        if (internet.isInternetConnection()){

            Toast.makeText(getApplicationContext(),"Class Updated",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), AllStudentPanel.class));
            finish();
        }else {

        }
    }


    private void updateCurrentStudentIdForDepartment(String uniqueId, String newStudentId) {
        DatabaseReference depRef = FirebaseDatabase.getInstance().getReference().child("major").child(uniqueId);

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

    private void getClassDataByUniqueId(String uniqueId) {
        DatabaseReference classRef = FirebaseDatabase.getInstance().getReference().child("class").child(uniqueId);

        classRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    Class cls = dataSnapshot.getValue(Class.class);
                    if (cls != null) {

                        classData = cls;
//                        String name = department.getmName();
//                        String currentStudentId = department.getcurrentId();

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

    private void deleteClassByUniqueId(String uniqueId) {
        DatabaseReference classRef = FirebaseDatabase.getInstance().getReference().child("class").child(uniqueId);

        classRef.removeValue()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getApplicationContext(), "Class data deleted successfully.", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Failed to delete Class data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

}