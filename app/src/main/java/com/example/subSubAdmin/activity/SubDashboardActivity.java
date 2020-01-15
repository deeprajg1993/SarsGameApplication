package com.example.subSubAdmin.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gameapplication.R;
import com.example.gameapplication.userApp.dailog.iOSDialog;
import com.example.gameapplication.userApp.dailog.iOSDialogBuilder;
import com.example.gameapplication.userApp.dailog.iOSDialogClickListener;
import com.example.gameapplication.userApp.utlis.BaseURL;
import com.example.gameapplication.userApp.utlis.CommonMethod;
import com.example.gameapplication.userApp.utlis.SessionManager;
import com.example.subSubAdmin.fragment.HomeFragment;
import com.example.subSubAdmin.model.ChipsPointResponseParser;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class SubDashboardActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    //  RecyclerView rcDriversList;
    Toolbar toolbars;
    ImageView ivNavigation;
    ProgressDialog progress;
    SessionManager sessionManager;
    HashMap<String, String> user;

    ActionBarDrawerToggle toggle;
    TextView tvChipsNumber, tvUsername, tvUserid;
    DrawerLayout drawer;
    LinearLayout llAddUser, llStatement;
    String chips_points;
    NavigationView navView;
    boolean doubleBackToExitPressedOnce = false;
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 5000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sub);
        progress = new ProgressDialog(this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);

        sessionManager = new SessionManager(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ivNavigation = findViewById(R.id.ivNavigation);
        ivNavigation.setOnClickListener(this);

        drawer = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.sub_nav_view);

        View header = navView.getHeaderView(0);
        tvUsername = header.findViewById(R.id.tvSubUserName);
        tvUserid = header.findViewById(R.id.tvSubUserid);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
        tvChipsNumber = findViewById(R.id.tvChipsNumber);
        tvChipsNumber.setText("Chips :" + CommonMethod.getChipsPoints(this));
        tvUserid.setText("₹ " + CommonMethod.getChipsPoints(this));

        if (sessionManager.getUserDetails() != null) {
            user = sessionManager.getUserDetails();

            chips_points = user.get(SessionManager.CHIPS_POINT);
            String total_User = user.get(SessionManager.TOTAL_USER);
            // totalUser.setText(total_User);
            // String userId = user.get(SessionManager.USER_ID);

            String dellerUser = user.get(SessionManager.USERNAME);
            String Name = dellerUser.substring(0, 1).toUpperCase() + dellerUser.substring(1);

            tvUsername.setText(Name);
        }

        gotoFragment(new HomeFragment(), "Home Fragment");


    }

    private void logout() {

        new iOSDialogBuilder(this)
                .setTitle("Logout")
                .setSubtitle("Are you sure do you want to logout?")
                .setBoldPositiveLabel(true)
                .setCancelable(false)
                .setPositiveListener("Yes", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {

                        sessionManager.logoutUser();
                        dialog.dismiss();
                        finish();
                    }
                })
                .setNegativeListener("No", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build().show();
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivNavigation:
                drawer = findViewById(R.id.drawer_layout);

                if (drawer.isDrawerOpen(Gravity.START)) {
                    drawer.closeDrawer(Gravity.START);
                } else {
                    drawer.openDrawer(Gravity.START);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit.", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
            return;
        }

        handler.removeCallbacks(runnable);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_sub_home) {
            gotoFragment(new HomeFragment(), "Home Fragment");

        } else if (id == R.id.nav_sub_logout) {
            logout();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


        return true;
    }

    private void gotoFragment(Fragment fragment, final String constant) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, fragment, constant)
                //.addToBackStack(null)
                .commit();
    }


    @Override
    public void onResume() {
        //start handler as activity become visible

        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                //do something
                if (CommonMethod.isOnline(SubDashboardActivity.this)) {
                    chipsPoints();
                    tvChipsNumber.setText("Chips :" + CommonMethod.getChipsPoints(SubDashboardActivity.this));
                    tvUserid.setText("₹ " + CommonMethod.getChipsPoints(SubDashboardActivity.this));

                }
                handler.postDelayed(runnable, delay);
            }
        }, delay);

        super.onResume();
        handler.removeCallbacksAndMessages(null);

    }

// If onPause() is not included the threads will double up when you
// reload the activity

       @Override
       public void onPause() {
           handler.removeCallbacks(runnable); //stop handler when activity not visible
           super.onPause();
       }
    public void chipsPoints() {
        /*final ProgressDialog progress = ProgressDialog.show(getContext(), "", "Please wait...", true);
        progress.setCancelable(false);*/

        final String url = BaseURL.CHIPSPOINT;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //              progress.dismiss();
                        System.out.println("Response===" + response);

                        try {
                            ChipsPointResponseParser mDashboard = new Gson().fromJson(response, ChipsPointResponseParser.class);

                            if (mDashboard.getStatus().equals("1")) {
                                String chips_point = mDashboard.getChips();
                                CommonMethod.saveChipsPoints(SubDashboardActivity.this, chips_point);

                                //     Toast.makeText(getContext(), "" + mDashboard.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //            progress.dismiss();
                        System.out.println("Error" + error.toString());
                        Toast.makeText(SubDashboardActivity.this, "Server Error!!", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", CommonMethod.getUserId(SubDashboardActivity.this));
                params.put("type", CommonMethod.getUserType(SubDashboardActivity.this));


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SubDashboardActivity.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }

}