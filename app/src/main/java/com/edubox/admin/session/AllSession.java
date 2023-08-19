package com.edubox.admin.session;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.edubox.admin.adapter.MonthPickerDialog;
import com.edubox.admin.adapter.SessionAdapter;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AllSession extends AppCompatActivity implements View.OnClickListener, SessionAdapter.OnItemClickListener, SessionAdapter.OnEditClickListener, SessionAdapter.OnDeleteClickListener {

    private TextView addDepButton;
    private Dialog dialog;
    private Layout layout;
    private schoolDep schoolDep;
    private Session d;

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


    private RecyclerView recyclerView;
    private List<Session> sessions;

    private ValueEventListener valueEventListener;
    private SessionAdapter sessionAdapter;
    private AlertDialog adialog;
    private SearchView searchView;
    private Session s;
    private EditText depName, depStart, depPhone, depEnd, depLocation;
    private int selectedYear;
    private int selectedMonth;

    public AllSession() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_all_session);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(AllSession.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        adialog = builder.create();
        adialog.show();
        sessions = new ArrayList<>();

        sessionAdapter = new SessionAdapter(getApplicationContext(),sessions, this, this, this);
        recyclerView.setAdapter(sessionAdapter);

//        adialog.show();



        internet = new Internet(this);
        if (internet.isInternetConnection()){
            fetchSessions();
        }else {
            fetchSessionsWithLocal();
        }
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

    private void fetchSessionsWithLocal() {

    }

    private void fetchSessions() {
        adialog.show();
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
        dialog.setContentView(R.layout.session_bottom_sheet);

        depName = dialog.findViewById(R.id.depNameId);
        depLocation = dialog.findViewById(R.id.depLocationId);
        depStart = dialog.findViewById(R.id.startId);
        depEnd = dialog.findViewById(R.id.endId);
        depPhone = dialog.findViewById(R.id.depPhoneId);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);
        ImageView cancelButton2 = dialog.findViewById(R.id.cancelButton2);
        Button saveDep = dialog.findViewById(R.id.signUpBtn);

        depStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMonthYear();
            }
        });

        depEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMonthYear();
            }
        });

        depLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endMonthYear();
            }
        });

        depPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endMonthYear();
            }
        });

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

                d = new Session();
                d.setId(new Admin().uniqueId());
                d.setSId(""+sId);
                d.setaYname(""+name);
                d.setsMonth(""+start);
                d.seteMonth(""+location);
                d.setaStatus(1);
                d.setsYear(""+end);
                d.seteYear(""+phone);
                d.setUniqueId(new Admin().uId());
                d.setUId(""+start+"/"+end+"-"+location+"/"+phone);

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
                        checkSession(d);
                    }else {
                        createSessionInLocal(d);
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
                    String selectedDate = String.format("%d-%02d", year, month + 1);
                    depStart.setText(""+(month+1));
                    depEnd.setText(""+year);
//                        Toast.makeText(this, "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();
                },
                selectedYear,
                selectedMonth);
        monthPickerDialog.setTitle("Select Month/Year");
        monthPickerDialog.show();
    }

    public void endMonthYear(){

        Calendar calendar = Calendar.getInstance();
        selectedYear = calendar.get(Calendar.YEAR);
        selectedMonth = calendar.get(Calendar.MONTH);

            MonthPickerDialog monthPickerDialog = new MonthPickerDialog(this,
                    (view, year, month, dayOfMonth) -> {
                        selectedYear = year;
                        selectedMonth = month;
                        // Do something with the selected year and month
                        // For example, display them in a TextView
                        String selectedDate = String.format("%d-%02d", year, month + 1);
                        depLocation.setText(""+(month+1));
                        depPhone.setText(""+year);
//                        Toast.makeText(this, "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();
                    },
                    selectedYear,
                    selectedMonth);
            monthPickerDialog.setTitle("Select Month/Year");
            monthPickerDialog.show();

    }

    private void createSessionInLocal(Session d) {

    }

    private void checkSession(Session d) {
        String userUsername = d.getaYname();
        String userPassword = d.getUId();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("session");
        Query checkUserDatabase = reference.orderByChild("aYname").equalTo(userUsername);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
//                        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        // Retrieve the child data using the userSnapshot
                        String phoneFromDB = userSnapshot.child("uId").getValue(String.class);
                        String mName = userSnapshot.child("aYname").getValue(String.class);
                        String IdFromDB = userSnapshot.child("id").getValue(String.class);
                        String sIdFromDB = userSnapshot.child("sId").getValue(String.class);
                        String sYearFromDB = userSnapshot.child("sYear").getValue(String.class);
                        String eYearFromDB = userSnapshot.child("eYear").getValue(String.class);
                        String sMonthFromDB = userSnapshot.child("sMonth").getValue(String.class);
                        String eMonthFromDB = userSnapshot.child("eMonth").getValue(String.class);

//                        Toast.makeText(getApplicationContext(), "" + sIdFromDB, Toast.LENGTH_SHORT).show();

                        // Perform actions with the retrieved data
                        if (phoneFromDB.equals(userPassword)) {

                            Toast.makeText(getApplicationContext(), "Session already exist!" + sIdFromDB, Toast.LENGTH_SHORT).show();

                            return; // Exit the loop and method since the user is found
                        }
                    }
                    Toast.makeText(getApplicationContext(), "Session Exist", Toast.LENGTH_SHORT).show();

                } else {
                    saveSessionInformationWithRealtime(d);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the cancellation
            }
        });
    }

    private void saveSessionInformationWithRealtime(Session d) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference sessionRef = firebaseDatabase.getReference("session");

        String key = sessionRef.push().getKey();
        String uniqueId = d.getUniqueId();
        s = new Session();
        s = d;
        s.setSync_key(key);
        s.setSync_status(1);
        Session finalS = s;

        sessionRef.child(uniqueId)
                .setValue(s)
                .addOnSuccessListener(aVoid -> {
                    // User information saved successfully
                    createSessionInLocal(finalS);
                    Toast.makeText(getApplicationContext(), "School Session created successfully", Toast.LENGTH_SHORT).show();
//                    finish(); // Finish sign-up activity and return to login screen
                })
                .addOnFailureListener(e -> {
                    // Failed to save user information
                    Toast.makeText(getApplicationContext(), "Failed to save Session information: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void saveSessionData(Session session) {
//        schoolDepDAO schoolDepDAO = new schoolDepDAO(database);
        if (internet.isInternetConnection()){

            Toast.makeText(getApplicationContext(),"Session Data Successfully Updated",Toast.LENGTH_SHORT).show();
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
        ArrayList<Session> searchList = new ArrayList<>();
        for (Session session2: sessions){
            if (session2.getaYname().toLowerCase().contains(text.toLowerCase())||session2.getsYear().toLowerCase().contains(text.toLowerCase())||session2.getsMonth().toLowerCase().contains(text.toLowerCase())){
                searchList.add(session2);
            }
        }
        if (searchList.isEmpty()){
            Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
        } else {
            sessionAdapter.searchSessionList(searchList);
        }
    }

    @Override
    public void onItemClick(Session session) {

    }

    @Override
    public void onEditClick(Session session) {
        setCurrentDialog(session);
    }

    private void setCurrentDialog(Session session) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.warning_alert_dialog,(ConstraintLayout)findViewById(R.id.alertDialog));
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText("Warning");
        ((TextView) view.findViewById(R.id.textMessage)).setText("Are you sure?");
        ((Button) view.findViewById(R.id.alertButtonYes)).setText("Yes");
        ((Button) view.findViewById(R.id.alertButtonNo)).setText("No");
        ((ImageView) view.findViewById(R.id.alertImage)).setImageResource(com.hbb20.R.drawable.notification_bg);

        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.alertButtonYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Toast.makeText(AllSession.this,"Success",Toast.LENGTH_SHORT).show();
            }
        });

        if (alertDialog.getWindow()!= null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();

    }

    @Override
    public void onDeleteClick(Session session) {

    }
}