package com.edubox.admin.routine.day.week;

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

public class FridayFragment extends Fragment {

    public FridayFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friday, container, false);

        TextView dateTextView = rootView.findViewById(R.id.dateTextView);
        dateTextView.setText(getFormattedDate(5)); // Pass the day of the week (2 for Monday) to get the date

        return rootView;
    }

    private String getFormattedDate(int dayOfWeek) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }
}
