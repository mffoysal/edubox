package com.edubox.admin.routine.month;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.edubox.admin.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DayFragment extends Fragment {

    private static final String ARG_DAY_OFFSET = "dayOffset";

    public DayFragment() {
    }

    public static DayFragment newInstance(int dayOffset) {
        DayFragment fragment = new DayFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_DAY_OFFSET, dayOffset);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_day, container, false);

        TextView dateTextView = rootView.findViewById(R.id.dateTextView);
        int dayOffset = getArguments().getInt(ARG_DAY_OFFSET);
        dateTextView.setText(getFormattedDate(dayOffset));

        return rootView;
    }

    private String getFormattedDate(int dayOffset) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_WEEK, dayOffset);
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }
}
