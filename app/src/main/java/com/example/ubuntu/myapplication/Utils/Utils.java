package com.example.ubuntu.myapplication.Utils;

import android.content.Context;
import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by ubuntu on 9/10/17.
 */

public class Utils {

    public static String dateMonthFormaterforNotification(String date, Context mContext) {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa", Locale.US);
        try {
            cal.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long epoch = cal.getTimeInMillis();
        String timePassedString = (String) DateUtils.getRelativeDateTimeString(mContext, epoch, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_NUMERIC_DATE);
        return timePassedString;
    }

    public static String getCurrentDateTime(Context context) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa", Locale.US);
        String formatted = sdf.format(cal.getTime());
        return formatted;
    }

}
