package com.example.linhnh.myapplication.util;

import android.text.TextUtils;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Envy 15T on 6/5/2015.
 */
public class StringUtil {

    /**
     * Upper case first letter in string
     *
     * @param str
     * @return
     */
    public static String UppercaseFirstLetters(String str) {
        boolean prevWasWhiteSp = true;
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isLetter(chars[i])) {
                if (prevWasWhiteSp) {
                    chars[i] = Character.toUpperCase(chars[i]);
                }
                prevWasWhiteSp = false;
            } else {
                prevWasWhiteSp = Character.isWhitespace(chars[i]);
            }
        }
        return new String(chars);
    }

    public static String formatDate(int year, int monthOfYear, int dayOfMonth) {
        return year + "/" + monthOfYear + "/" + dayOfMonth;
    }

    public static String formatDateJP(int year, int monthOfYear, int dayOfMonth) {
        return year + "年" + monthOfYear + "月" + dayOfMonth + "日";
    }

    public static String formatDate(Calendar calendar) {
        return formatDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static String formatDateJP(Calendar calendar) {
        return formatDateJP(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static boolean checkStringValid(String data) {
        if (data != null && !data.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkStringNull(String data) {
        if (data != null ) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkZero(String data){
        if(checkStringValid(data) && !data.equals("0"))
            return true;
        else
            return false;
    }

    public static void displayText(String text, TextView textView) {
        if(textView == null)
            return;
        if (text != null && !text.isEmpty() && !text.equals("null")) {
            textView.setText(text);
        } else {
            textView.setText("");
        }

    }
    public static void displayText(String text, TextView textView, String prefix ) {
        if(textView == null)
            return;
        if (text != null && !text.isEmpty() && !text.equals("null")) {
            textView.setText(text + prefix);
        } else {
            textView.setText("0.00"+prefix);
        }

    }
    public static void displayText(Float text, TextView textView) {
        if (text != null ) {
            String.format("%.0f", text);
        } else {
            textView.setText("");
        }
    }
    public static void displayText(int text, TextView textView) {
        if (text > 0) {
            textView.setText(text + "");
        } else {
            textView.setText("");
        }
    }

    /**
     * Check valid email
     *
     * @param target
     * @return true if valid email, false if invalid email
     */
    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static void fillPrefix(TextView tv, float value, String prefix) {
        if (value == 0) {
            tv.setText("0.00"+ prefix);
        } else if (value > 0) {
            if (!convertTextFloattoInt(String.valueOf(value))) {
                tv.setText(String.format("%.0f", value) + prefix);
            } else {
                tv.setText(String.format("%.2f", value) + prefix);
            }
        } else {
            tv.setText("");
        }
    }

    /**
     * Ellipsize string with maxCharacter
     * @param input
     * @param maxCharacters
     * @return
     */
    public static String ellipsize(String input, int maxCharacters) {
        if(maxCharacters < 3) {
            throw new IllegalArgumentException("maxCharacters must be at least 3 because the ellipsis already take up 3 characters");
        }
        if (input == null || input.length() < maxCharacters) {
            return input;
        }
        return input.substring(0, maxCharacters - 3) + "...";
    }

    public static boolean checkTypeFloat(String value){
        if(value.contains("."))
        {
            return true;
        }
        return  false;
    }
    public static boolean convertTextFloattoInt(String value){
        String result5[] = value.split("[.]");
        int b = Integer.parseInt(result5[1]);
        if(b>0){
            return true;
        }
        return false;
    }
}
