package com.edubox.admin.std;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edubox.admin.R;
import com.edubox.admin.adapter.StudentAdapter;

import java.util.ArrayList;
import java.util.List;

public class AllStudentPanel extends AppCompatActivity implements View.OnClickListener, StudentAdapter.OnItemClickListener, StudentAdapter.OnEditClickListener, StudentAdapter.OnDeleteClickListener {

    RecyclerView recyclerView;
    List<student> dataList;
    StudentAdapter adapter;
    student androidData;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_student_panel);

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.search);

        searchView.clearFocus();
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

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        dataList = new ArrayList<>();

        androidData = new student();
        androidData.setstdName("Farhad Foysal 1");
        androidData.setstdId("221005312");
        dataList.add(androidData);

        androidData = new student();
        androidData.setstdName("Farhad Foysal 2");
        androidData.setstdId("221005313");
        dataList.add(androidData);

        androidData = new student();
        androidData.setstdName("Farhad Foysal 3");
        androidData.setstdId("221005314");
        dataList.add(androidData);

        androidData = new student();
        androidData.setstdName("Farhad Foysal 4");
        androidData.setstdId("221005315");
        dataList.add(androidData);

        adapter = new StudentAdapter(getApplicationContext(), dataList, this, this, this);
        recyclerView.setAdapter(adapter);
    }

    private void searchList(String text){
        List<student> dataSearchList = new ArrayList<>();
        for (student data : dataList){
            if (data.getStdId().toLowerCase().contains(text.toLowerCase())) {
                dataSearchList.add(data);
            }
        }
        if (dataSearchList.isEmpty()){
            Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setSearchList(dataSearchList);
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(student sub) {
        Toast.makeText(this,""+sub.getStdId(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEditClick(student sub) {

    }

    @Override
    public void onDeleteClick(student sub) {

    }
}