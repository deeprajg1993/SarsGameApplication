package com.example.gameapplication.userApp.utlis;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.core.content.ContextCompat;

import com.example.gameapplication.R;
import com.google.android.material.snackbar.Snackbar;


public class CommonMethod {

    public static void displaySnackbarLogin(View view, Context context, String s) {
        Snackbar snack = Snackbar.make(view, s, Snackbar.LENGTH_LONG);
        View sbview = snack.getView();
        sbview.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        Snackbar.make(sbview.findViewById(android.R.id.content),
                R.string.test, Snackbar.LENGTH_INDEFINITE).show();
       /* TextView textView = (TextView) sbview.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(context.getResources().getColor(R.color.white));*/
        snack.show();
    }


    public static boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo;
        try {
            netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }

    public static void closeKeyboard(final Context context, final View view) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void saveUserId(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Hrms", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USER", value);
        editor.commit();

    }

    public static String getUserId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Hrms", 0);
        String login_user_id = sharedPreferences.getString("USER", "");
        return login_user_id;

    }

    //chips point
    public static void saveChipsPoints(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Hrms", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("CHIPS_POINTS", value);
        editor.commit();

    }

    public static String getChipsPoints(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Hrms", 0);
        String login_chipa_point= sharedPreferences.getString("CHIPS_POINTS", "");
        return login_chipa_point;

    }

    //user types login
    public static void saveUserType(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Hrms", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USERTYPE", value);
        editor.commit();

    }

    public static String getUserType(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Hrms", 0);
        String login_user_type = sharedPreferences.getString("USERTYPE", "");
        return login_user_type;

    }
    //user types login
    public static void saveTotalUser(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Hrms", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("TOTAL_USER", value);
        editor.commit();

    }

    public static String getTotalUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Hrms", 0);
        String login_total_user = sharedPreferences.getString("TOTAL_USER", "");
        return login_total_user;

    }
}