package com.edubox.admin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.edubox.admin.R;
import com.edubox.admin.cls.AllClasses;
import com.edubox.admin.std.StudentsPage;
import com.edubox.admin.subject.AllSubjects;


public class homeFrag extends Fragment {

    private CardView cardView, stdView, classView, courseView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_home, container, false);


        stdView = view.findViewById(R.id.dashCard3Id);
        classView = view.findViewById(R.id.dashCard5Id);
        courseView = view.findViewById(R.id.dashCard6Id);
        courseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AllSubjects.class);
                startActivity(intent);
            }
        });
        classView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AllClasses.class);
                startActivity(intent);
            }
        });
        stdView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StudentsPage.class);
                startActivity(intent);
            }
        });




        return view;
    }
}