package com.edubox.admin.fcomf;

import android.os.Build;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class TimeUtil {

    static SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aaa");

    public static String getCurrentTime() {
        Date loginTime = new Date();
        try {
            return formatter.format(loginTime);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("deprecation")
    public static int getDayDifference(String strDate1, String strDate2) {
        if (strDate1.isEmpty() || strDate2.isEmpty()) {
            return -1;
        }
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM,yyyy");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = dateFormatter.parse(strDate1);
            date2 = dateFormatter.parse(strDate2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        LocalDate dateOne = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dateOne = LocalDate.of(date1.getYear(), date1.getMonth() + 1, date1.getDate());
        }
        LocalDate dateTwo = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dateTwo = LocalDate.of(date2.getYear(), date2.getMonth() + 1, date2.getDate());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Math.abs(dateTwo.getDayOfMonth() - dateOne.getDayOfMonth());
        }
        return 0;
    }

    public static long getTimeDifference(String time1, String time2) {
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = formatter.parse(time1);
            date2 = formatter.parse(time2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date2.getTime() - date1.getTime();
    }

    public static void main(String[] args) {
        System.out.println(getTimeDifference(getCurrentTime(), getCurrentTime()));
    }
}
