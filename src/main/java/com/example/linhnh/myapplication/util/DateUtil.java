package com.example.linhnh.myapplication.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by User on 10/7/2015.
 */
public class DateUtil {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    /**
     * Calculator age
     * @param dateOfBirth
     * @return age
     */
    public static int getAgeByBirthDay(String dateOfBirth) {
        if (TextUtils.isEmpty(dateOfBirth))
            return 0;

        int age = 0;

        try {
            Calendar now = Calendar.getInstance();
            Calendar dob = Calendar.getInstance();
            Date date = DATE_FORMAT.parse(dateOfBirth.trim());
            dob.setTime(date);
            if (dob.after(now)) {
                throw new IllegalArgumentException("Can't be born in the future");
            }
            int year1 = now.get(Calendar.YEAR);
            int year2 = dob.get(Calendar.YEAR);
            age = year1 - year2;
            int month1 = now.get(Calendar.MONTH);
            int month2 = dob.get(Calendar.MONTH);
            if (month2 > month1) {
                age--;
            } else if (month1 == month2) {
                int day1 = now.get(Calendar.DAY_OF_MONTH);
                int day2 = dob.get(Calendar.DAY_OF_MONTH);
                if (day2 > day1) {
                    age--;
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return age;
    }

    public static Calendar getBirthDayCalendar(String dateOfBirth) {
        if (dateOfBirth == null) {
            return Calendar.getInstance();
        }
        try {
            Date bd = DATE_FORMAT.parse(dateOfBirth);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(bd);
            return calendar;
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }
        return Calendar.getInstance();
    }

    public static String getDateString(Date date) {
        return DATE_FORMAT.format(date);
    }

    /**
     * Convert date string to date string by format type
     *
     * @param date
     * @param typeInput
     * @param typeOutput
     * @param locale
     * @return string date follow output type
     */
    public static String convertDate(String date, String typeInput,
                                     String typeOutput, Locale locale) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(typeInput, locale);
        SimpleDateFormat outputFormat = new SimpleDateFormat(typeOutput, locale);
        Date inputDate = null;
        String outputDate = null;
        try {
            inputDate = inputFormat.parse(date);
            outputDate = outputFormat.format(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDate;
    }
}
