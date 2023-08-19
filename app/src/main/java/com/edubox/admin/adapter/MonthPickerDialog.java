package com.edubox.admin.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.lang.reflect.Field;

public class MonthPickerDialog extends DatePickerDialog {

    public MonthPickerDialog(@NonNull Context context, @Nullable OnDateSetListener listener,
                             int year, int month) {
        super(context, getTheme(context), listener, year, month, 1); // Day is set to 1 to hide it
        hideDayField();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MonthPickerDialog(@NonNull Context context, int theme, @Nullable OnDateSetListener listener,
                             int year, int month) {
        super(context, theme, listener, year, month, 1);
        hideDayField();
    }

    private static int getTheme(Context context) {
        // You can customize the theme as needed or use your app's default theme.
        return android.R.style.Theme_DeviceDefault_Light_Dialog;
    }

    private void hideDayField() {
        try {
            // Use reflection to access the private member 'mDatePicker' of DatePickerDialog
            Field[] datePickerDialogFields = DatePickerDialog.class.getDeclaredFields();
            for (Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker")) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker = (DatePicker) datePickerDialogField.get(this);
                    if (datePicker != null) {
                        // Set the 'day' field to be hidden (spinners will still show, but with 1 item)
                        Field dayField = DatePicker.class.getDeclaredField("mDaySpinner");
                        dayField.setAccessible(true);
                        dayField.set(datePicker, null);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
