package com.edubox.admin.std;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.edubox.admin.Admin;
import com.edubox.admin.DatabaseManager;
import com.edubox.admin.Internet;
import com.edubox.admin.Logout;
import com.edubox.admin.Network;
import com.edubox.admin.R;
import com.edubox.admin.User;
import com.edubox.admin.UserDAO;
import com.edubox.admin.adapter.MonthPickerDialog;
import com.edubox.admin.login.LoginMainActivity;
import com.edubox.admin.scl.School;
import com.edubox.admin.scl.SchoolCallback;
import com.edubox.admin.scl.SchoolDAO;
import com.edubox.admin.scl.schoolDep;
import com.edubox.admin.user.UserCallback;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AddStudentPanel extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {


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

    private DatabaseReference usersRef;
    private UserCallback userCallback;
    private BroadcastReceiver connectivityReceiver;
    private Intent intent;
    private ActionBar actionbar;

    private User user;
    private String userPhone, sId;
    private School school;
    private SchoolCallback schoolCallback;



    private List<schoolDep> schoolDeps;
    private ValueEventListener valueEventListener;


    private List<String> genders = new ArrayList<>();
    private List<String> religions = new ArrayList<>();
    private ArrayAdapter<schoolDep> adapter;
    private ArrayAdapter<String> genderAdapter;
    private ArrayAdapter<String> religionAdapter;

    private List<student> studentList;
    private student student;
    private AutoCompleteTextView autoCompleteTextView, gender, religion, session;
    private TextView textView;
    private Spinner spinner;
    private schoolDep selectedItem;
    private schoolDep schoolDep;
    private int selectedYear;
    private int selectedMonth;
    private TextInputEditText stddob, stdId ;
    private EditText stdName, stdPhone, stdEmail, stdAddress,  fatherName, motherName, fatherPhone, motherPhone, fatherNid, motherNid;
    private EditText stdPass, stdNidBirth, guardianName, guardianPhone, guardianAddress, guardianEmail;

    private Button saveStd;
    private student s;
    private String studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_student_panel);


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




        gender = findViewById(R.id.genderId);
        religion = findViewById(R.id.religionId);

        stdName = findViewById(R.id.stdNameid);
        stdPhone = findViewById(R.id.stdPhoneid);
        stdEmail = findViewById(R.id.stdEmailid);
        stdPass = findViewById(R.id.stdPassid);
        stdAddress = findViewById(R.id.stdAddressid);
        stdId = findViewById(R.id.stdId);
        fatherName = findViewById(R.id.fatherNameid);
        motherName = findViewById(R.id.motherNameid);
        fatherPhone = findViewById(R.id.fatherPhoneid);
        fatherNid = findViewById(R.id.fatherNidid);
        motherPhone = findViewById(R.id.motherPhoneid);
        motherNid = findViewById(R.id.motherNidid);
        stdNidBirth = findViewById(R.id.stdNidid);
        guardianName = findViewById(R.id.guardianNameid);
        guardianAddress = findViewById(R.id.guardianAddressid);
        guardianPhone = findViewById(R.id.guardianPhoneid);
        guardianEmail = findViewById(R.id.guardianEmailid);

        stddob = findViewById(R.id.stddobId);
        stddob.setOnClickListener(this);

        saveStd = findViewById(R.id.saveStdButton);
        saveStd.setOnClickListener(this);

        schoolDeps = new ArrayList<>();
        autoCompleteTextView = findViewById(R.id.selectDepartmentId);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                schoolDep = (schoolDep) adapterView.getItemAtPosition(i);
//                String nameee = schoolDep.getmName();
                String nameee = schoolDep.getUniqueId();
                Toast.makeText(getApplicationContext(),""+nameee,Toast.LENGTH_SHORT).show();
            }
        });

        adapter = new ArrayAdapter<>(this,R.layout.custom_spinner_item,schoolDeps);
        autoCompleteTextView.setAdapter(adapter);

        if (internet.isInternetConnection()){
            fetchDeps();
        }else {
//            fetchDepsWithLocal();
        }


        genders.add("Male");
        genders.add("Female");
        genders.add("Other");
        religions.add("Islam");
        religions.add("Hindu");
        religions.add("Cristian");
        religions.add("Buddhist");
        religions.add("Other");

        genderAdapter = new ArrayAdapter<>(this,R.layout.custom_spinner_item,genders);
        gender.setAdapter(genderAdapter);
        religionAdapter = new ArrayAdapter<>(this,R.layout.custom_spinner_item,religions);
        religion.setAdapter(religionAdapter);

    }

    public void selectDate(){
        Calendar calendar = Calendar.getInstance();
        int yearNow = calendar.get(Calendar.YEAR);
        int monthNow = calendar.get(Calendar.MONTH);
        int dateNow = calendar.get(Calendar.DAY_OF_MONTH);

        Locale id = new Locale("in","ID");
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy",id);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                stddob.setText(""+i2+"/"+(i1+1)+"/"+i);
            }
        },yearNow,monthNow,dateNow);
        datePickerDialog.show();
    }

    public void startMonthYear(){
        Calendar calendar = Calendar.getInstance();
        selectedYear = calendar.get(Calendar.YEAR);
        selectedMonth = calendar.get(Calendar.MONTH);

        MonthPickerDialog monthPickerDialog = new MonthPickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    selectedYear = year;
                    selectedMonth = month;
                    // Do something with the selected year and month
                    // For example, display them in a TextView
                    String selectedDate = String.format("%02d-%02d-%d",dayOfMonth, month + 1, year);
                    stddob.setText(selectedDate);
//                        Toast.makeText(this, "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();
                },
                selectedYear,
                selectedMonth);
        monthPickerDialog.setTitle("Select Month/Year");
        monthPickerDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {



    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.stddobId){
//            selectDate();
            startMonthYear();
        } else if (view.getId()==R.id.saveStdButton) {

            String name = stdName.getText().toString();
            String phone = stdPhone.getText().toString();
            String email = stdEmail.getText().toString().trim();
            String address = stdAddress.getText().toString().trim();
            String stdid = stdId.getText().toString().trim();
            String stdgender = gender.getText().toString().trim();
            String stdreligion = religion.getText().toString().trim();
            String depUniqueId = schoolDep.getUniqueId();
            String dep = schoolDep.getmName();
            String Dob = stddob.getText().toString();
            String pass = stdPass.getText().toString();
            String fName = fatherName.getText().toString();
            String mName = motherName.getText().toString();
            String fNid = fatherNid.getText().toString();
            String mNid = motherNid.getText().toString();
            String fPhone = fatherPhone.getText().toString();
            String mPhone = motherPhone.getText().toString();
            String gName = guardianName.getText().toString();
            String gPhone = guardianPhone.getText().toString();
            String gEmail = guardianEmail.getText().toString();
            String gAddress = guardianAddress.getText().toString();

            getDepartmentDataByUniqueId(schoolDep.getUniqueId());
            if(schoolDep.getcurrentId()==null){

                try {
                    studentId = ""+(Integer.parseInt(schoolDep.getstartId())+1);
                }catch (Exception e){

                }

            }else {
                studentId = ""+(Integer.parseInt(schoolDep.getcurrentId())+1);
            }

            student = new student();
            student.setdob(""+Dob);
            student.setgName(""+gName);
            student.setgAddress(""+gAddress);
            student.setgPhone(""+gPhone);
            student.setgEmail(""+gEmail);
            student.setStudentId(""+studentId);
            student.setUId(""+studentId);
            student.setSId(""+sId);
            student.setId(new Admin().uniqueId());
            student.setstdId(""+stdid);
            student.setUniqueId(new Admin().uId());
            student.setstdName(""+name);
            student.setAddress(""+address);
            student.setaStatus(1);
            student.setAdmissionDate(new Admin().getCurrentDateTime());
            student.setstdPhone(""+phone);
            student.setstdPass(""+pass);
            student.setstdEmail(""+email);
            student.setfatherName(""+fName);
            student.setmotherName(""+mName);
            student.setfNid(""+fNid);
            student.setmNid(""+mNid);
            student.sethomePhone(""+fPhone);
            student.setUnionWord(""+mPhone);
            student.setgender(""+stdgender);
            student.setstdReligion(""+stdreligion);
            student.setMajor(""+dep);
            student.setsMajor(""+depUniqueId);

            if (phone.isEmpty()){
                stdPhone.setError("Phone cannot be empty");
            }
            if (email.isEmpty()){
                stdEmail.setError("Email cannot be empty");
            }
            if (name.isEmpty()){
                stdName.setError("Name cannot be empty");
            } else{

                if (internet.isInternetConnection()){
                    checkStudent(student);
                }else {
                    createStudentInLocal(student);
                }

            }

        }
    }

    private void createStudentInLocal(com.edubox.admin.std.student student) {

    }

    private void checkStudent(student d) {

            String userUsername = d.getStdName();
            String userPassword = d.getstdPhone();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("students");
            Query checkUserDatabase = reference.orderByChild("mName").equalTo(userUsername);
            checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
//                        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                            // Retrieve the child data using the userSnapshot
                            String phoneFromDB = userSnapshot.child("stdPhone").getValue(String.class);
                            String mName = userSnapshot.child("stdName").getValue(String.class);
                            String IdFromDB = userSnapshot.child("id").getValue(String.class);
                            String sIdFromDB = userSnapshot.child("sId").getValue(String.class);
                            String studentIdFromDB = userSnapshot.child("studentId").getValue(String.class);
                            String uIdFromDB = userSnapshot.child("uId").getValue(String.class);

//                        Toast.makeText(getApplicationContext(), "" + sIdFromDB, Toast.LENGTH_SHORT).show();

                            // Perform actions with the retrieved data
                            if (phoneFromDB.equals(userPassword)) {

                                Toast.makeText(getApplicationContext(), "Student already exist!" + sIdFromDB, Toast.LENGTH_SHORT).show();

                                return; // Exit the loop and method since the user is found
                            }
                        }
                        Toast.makeText(getApplicationContext(), "Student Exist", Toast.LENGTH_SHORT).show();

                    } else {
                        saveStudentInformationWithRealtime(d);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle the cancellation
                }
            });

    }

    private void saveStudentInformationWithRealtime(student d) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference depRef = firebaseDatabase.getReference("students");

        String key = depRef.push().getKey();
        String uniqueId = d.getUniqueId();
        s = new student();
        s = d;
        s.setSync_key(key);
        s.setSync_status(1);
        student finalS = s;

        depRef.child(uniqueId)
                .setValue(s)
                .addOnSuccessListener(aVoid -> {
                    // User information saved successfully
                    createStudentInLocal(finalS);
                    Toast.makeText(getApplicationContext(), "Student Account created successfully", Toast.LENGTH_SHORT).show();
//                    finish(); // Finish sign-up activity and return to login screen
                })
                .addOnFailureListener(e -> {
                    // Failed to save user information
                    Toast.makeText(getApplicationContext(), "Failed to save Student information: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void saveStudentData(student d) {
//        studentDAO studentDAO = new studentDAO(database);
        if (internet.isInternetConnection()){

            Toast.makeText(getApplicationContext(),"Student Account Successfully Updated",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), AllStudentPanel.class));
            finish();
        }else {

        }
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
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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

    private void getDepartmentDataByUniqueId(String uniqueId) {
        DatabaseReference depRef = FirebaseDatabase.getInstance().getReference().child("major").child(uniqueId);

        depRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    schoolDep department = dataSnapshot.getValue(schoolDep.class);
                    if (department != null) {

                        schoolDep = department;
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


}