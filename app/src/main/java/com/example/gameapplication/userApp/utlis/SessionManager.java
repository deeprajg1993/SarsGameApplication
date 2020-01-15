package com.example.gameapplication.userApp.utlis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.gameapplication.userApp.activity.MainActivity;
import com.example.gameapplication.userApp.activity.LoginActivity;
import com.example.gameapplication.userApp.model.LoginResponse;

import java.util.HashMap;

public class SessionManager {
    public static final String ID = "";
    public static final String USER_ID = "user_id";
    public static final String USERNAME = "username";
    public static final String CHIPS_POINT = "chips_point";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String PREF_NAME = "Hrms";
    public static final String TOTAL_USER = "total_user";


    SharedPreferences pref;
    SharedPreferences.Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(LoginResponse loginDetails) {

        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(USER_ID, loginDetails.getId());
        editor.putString(USERNAME, loginDetails.getUser_id());
        editor.putString(CHIPS_POINT, loginDetails.getChip_point());
        editor.putString(TOTAL_USER, loginDetails.getTotal_user());
        editor.commit();
    }


    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkLogin() {
        // Check login status
        if (this.isLoggedIn()) {

            //   if (isVerified().equals("1")) {
            Intent i = new Intent(_context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
          /*  } else {
                Intent i = new Intent(_context, IdentityVerification.class);
                _context.startActivity(i);
            }*/

        } else {
            Intent i = new Intent(_context, LoginActivity.class);
            // Staring Login Activity
            _context.startActivity(i);
        }
    }

    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(USERNAME, pref.getString(USERNAME, null));
        user.put(USER_ID, pref.getString(USER_ID, null));
        user.put(CHIPS_POINT, pref.getString(CHIPS_POINT, null));
        user.put(TOTAL_USER, pref.getString(TOTAL_USER, null));
        // return user
        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        editor.clear();
        editor.commit();
        Intent i = new Intent(_context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

}