package com.edubox.admin;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import com.edubox.admin.cls.Class;
import com.edubox.admin.scl.School;
import com.edubox.admin.scl.SchoolCallback;
import com.edubox.admin.scl.schoolDep;
import com.edubox.admin.session.Session;
import com.edubox.admin.std.student;
import com.edubox.admin.user.UserCallback;
import com.edubox.admin.web.WebServerTwo;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class oneFrag extends DialogFragment implements View.OnClickListener {

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

    private student std;

    private SchoolCallback schoolCallback;


    private com.edubox.admin.scl.schoolDep schoolDep;
    private Session session;
    private List<schoolDep> schoolDeps;
    private ArrayAdapter<schoolDep> departmentAdapter;
    private ValueEventListener valueEventListener;
    private List<Session> sessions = new ArrayList<>();
    private ArrayAdapter<Session> sessionAdapter;


    private Map<String, Integer> programs = new HashMap<>();
    private ArrayAdapter<String> programsArrayAdapter;
    private AutoCompleteTextView program, departmentSel, sessionSel;
    private int programDay = 1;
    private Button saveClass;
    private TextInputEditText classesText, sectionsText;
    private Class classData;
    private Class s;
    private EditText editText1, editText2;
    private Button bt1,bt2;
    private TextView textView1,textView2, sessionText;
    private ImageView qrId;
    private Dialog dialog;
    private Spinner sessionSpinner;
    private CardView sessionCard;
    private student student;
    private Session sname;

    private WebServerTwo webServer;
    private static final int PORT = 3697;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        startWebServer();


        View view = inflater.inflate(R.layout.fragment_one, container, false);

        databaseManager = new DatabaseManager(getActivity());
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

        intent = getActivity().getIntent();
        logout = new Logout(getActivity());
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

//        editText1 = (EditText) view.findViewById(R.id.num1);
//        editText2 = (EditText) view.findViewById(R.id.num2);
        bt1 = view.findViewById(R.id.clearButtonId);
        bt2 = view.findViewById(R.id.submitButtonId);
        textView1 = view.findViewById(R.id.resultId);
        qrId = view.findViewById(R.id.qrImageView);
        sessionCard = view.findViewById(R.id.sessionCard);
        sessionText = view.findViewById(R.id.sessionName);

//        Bitmap qrBitmap = generateQRCode(user.getUserId());
        Bitmap qrBitmap = generateQRCode("Farhad Foysal");
        qrId.setImageBitmap(qrBitmap);

        Bitmap barcodeBitmap = generateBarcode("221005312");
        qrId.setImageBitmap(barcodeBitmap);
//        generateDisplayBarcode("221005312");

        sessionCard.setOnClickListener(this);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        internet = new Internet(getActivity());
        connectivityReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction() != null && intent.getAction().equals(Network.ACTION_CONNECTIVITY_CHANGE)) {
                    boolean isConnected = intent.getBooleanExtra(Network.EXTRA_CONNECTIVITY_STATUS, false);
                    if (isConnected) {
//                        Toast.makeText(getApplicationContext(),"Internet Connected",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_SHORT).show();

                    }
                }
            }
        };

        intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("student")) {
            student = (student) intent.getSerializableExtra("student");

            String stdName = student.getStdName();
            String stdPhone = student.getstdPhone();
            String stdId = student.getStdId();
            String sId = student.getSId();

            if (student.getCurrSessId()!=null){

                internet = new Internet(getActivity());
                if (internet.isInternetConnection()){
                    Log.e("session","connection");
                    fetchSessionsOther();
                }else {
                    fetchSessionsWithLocalOther();

                }

                searchStudentForSession(student.getCurrSessId());


            }


        }



        return view;
    }

    private void searchStudentForSession(String sessionId) {
        sname = searchStudentById(sessions, sessionId);
        if (sname != null) {
            sessionText.setText(sname.getaYname());
        } else {
            sessionText.setText(student.getCurrSessId());
        }
    }


    private void fetchSessionsWithLocalOther() {

    }

    private Session getSessionData(String currSessId) {

        Session foundSession = searchStudentById(sessions,currSessId);

        if (foundSession != null) {
            return foundSession;
        }
        return null;
    }

    @Override
    public void onClick(View view) {

//        String nums = editText1.getText().toString();
//        String numss = editText2.getText().toString();

        if(view.getId()==R.id.clearButtonId) {
            try {
                stopWebServer();
//                double num = Double.parseDouble(nums);
//                double numm = Double.parseDouble(numss);
//                textView1.setText("Result is "+ (num-numm));
            }catch (Exception e){
                Toast.makeText(getContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(getContext(), "Sub Button is Clicked", Toast.LENGTH_SHORT).show();
        } else if (view.getId()==R.id.submitButtonId) {
            try {
                Toast.makeText(getContext(),"Submit Button start is Clicked",Toast.LENGTH_SHORT).show();


//                double num = Double.parseDouble(nums);
//                double numm = Double.parseDouble(numss);
//                textView1.setText("Result is "+ (num+numm));
            }catch (Exception e){
                Toast.makeText(getContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
            }
            startWebServer();

            Toast.makeText(getContext(),"Submit Button is Clicked",Toast.LENGTH_SHORT).show();
        }else if (view.getId()==R.id.sessionCard){
//            if (!isConnected()){
//                showInternetDialog();
//            }
            showCustomDialog();
        }
    }

    private void startWebServer() {
//        Log.d("WebServer", "Web server started at http://localhost:" + PORT);

        try {
            webServer = new WebServerTwo(getContext(),PORT);
            webServer.start();
            Log.d("WebServer", "Web server started at http://localhost:" + PORT);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("WebServer", "Error starting the web server: " + e.getMessage());
        }
    }

    private void stopWebServer() {
        if (webServer != null) {
            webServer.stop();
            Log.d("WebServer", "Web server stopped");
        }
    }

    private void showCustomDialog() {
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCancelable(true);
        sessionSpinner = dialog.findViewById(R.id.spinnerSession);

        sessions = new ArrayList<>();
        sessionAdapter = new ArrayAdapter<>(getActivity(),R.layout.custom_spinner_item,sessions);
        sessionSpinner.setAdapter(sessionAdapter);
        internet = new Internet(getActivity());
        if (internet.isInternetConnection()){
            fetchSessions();
        }else {
            fetchSessionsWithLocal();
        }
        sessionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                session = (Session) adapterView.getItemAtPosition(i);
                Toast.makeText(getActivity(),"Session : "+session.getId(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button dsave = dialog.findViewById(R.id.addTask);

        dsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                updateSession();
                
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void fetchSessionsWithLocal() {

    }

    private void updateSession() {
        if (internet.isInternetConnection()){
            updateSessionWithOnline();
        }else {
            updateSessionWithLocal(student.getUniqueId(),session.getUniqueId());
        }
    }

    private void updateSessionWithLocal(String uniqueId, String sessionId) {


    }

    public Session searchStudentById(List<Session> sessionss, String uniqueId) {
        Log.e("session","foundd");
        for (Session session1 : sessionss) {
            Log.e("session","Comparing: " + uniqueId + " and " + session1.getUniqueId());
            if (session1.getUniqueId().equals(uniqueId)) {
                return session1;
            }
        }
        return null;
    }

    private void updateSessionWithOnline() {
        updateUserSessionId(student.getUniqueId(),session.getUniqueId());
    }

    private void updateUserSessionId(String uniqueId, String sessionId) {
        DatabaseReference depRef = FirebaseDatabase.getInstance().getReference().child("students").child(uniqueId);

        Map<String, Object> updates = new HashMap<>();
        updates.put("currSessId", sessionId);

        depRef.updateChildren(updates)
                .addOnSuccessListener(aVoid -> {
                    updateSessionWithLocal(student.getUniqueId(),session.getUniqueId());
                    Toast.makeText(getContext(), "Current Session Id updated for the department.", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {

                    Toast.makeText(getContext(), "Failed to update Current Session Id: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }


    private void fetchSessions() {
        valueEventListener = sessionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sessions.clear();
                sessions.add(new Session("Select a Session"));
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

    private void fetchSessionsOther() {
        valueEventListener = sessionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sessions.clear();
//                sessions.add(new Session("Select a Session"));
                for (DataSnapshot itemSnapshot: snapshot.getChildren()) {
                    Session session = itemSnapshot.getValue(Session.class);
                    if (session != null && session.getSId().equals(sId)) {
                        session.setKey(itemSnapshot.getKey());
                        Log.e("session","found");
                        sessions.add(session);
                    }
                }
                searchStudentForSession(student.getCurrSessId());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void showInternetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Please connect to the internet to proceed further").setCancelable(false).setPositiveButton("Connect", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).create().show();

    }


    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo dataConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wifiConn!= null && wifiConn.isConnected()) || (dataConn != null && dataConn.isConnected())){
            return true;
        }else {
            return false;
        }

    }

    private Bitmap generateQRCode(String textToEncode) {
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        try {
            BitMatrix bitMatrix = barcodeEncoder.encode(textToEncode, BarcodeFormat.QR_CODE, 300, 300);
            return barcodeEncoder.createBitmap(bitMatrix);
        } catch (WriterException e) {
            Log.e("QRCodeGenerator", "Error generating QR code", e);
            return null;
        }
    }

    private Bitmap generateBarcode(String textToEncode) {
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        try {
            BitMatrix bitMatrix = barcodeEncoder.encode(textToEncode, BarcodeFormat.CODE_128, 1000, 600);
            return barcodeEncoder.createBitmap(bitMatrix);
        } catch (WriterException e) {
            Log.e("BarcodeGenerator", "Error generating barcode", e);
            return null;
        }
    }

    private Bitmap generateCode128Barcode(String textToEncode) {
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        try {
            BitMatrix bitMatrix = barcodeEncoder.encode(textToEncode, BarcodeFormat.CODE_128, 100, 100);
            return barcodeEncoder.createBitmap(bitMatrix);
        } catch (WriterException e) {
            Log.e("Code128BarcodeGenerator", "Error generating Code 128 barcode", e);
            return null;
        }
    }

    private Bitmap generateDisplayBarcode(String numericValue) {
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        try {
            Bitmap barcodeBitmap = barcodeEncoder.encodeBitmap(numericValue, BarcodeFormat.CODE_128, 600, 300);
            qrId.setImageBitmap(barcodeBitmap);
            return barcodeBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}