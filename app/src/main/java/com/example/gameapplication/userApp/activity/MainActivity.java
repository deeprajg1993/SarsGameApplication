package com.example.gameapplication.userApp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.gameapplication.userApp.ui.battinglist.BattingListFragment;
import com.example.gameapplication.userApp.ui.home.HomeFragment;
import com.example.gameapplication.userApp.utlis.BaseURL;
import com.example.gameapplication.userApp.utlis.CommonMethod;
import com.example.gameapplication.userApp.utlis.SessionManager;
import com.example.subSubAdmin.model.ChipsPointResponseParser;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    public static BottomNavigationView bottomNavigation;
    Context context;
    NavigationView navView;
    TextView tvUsername, tvUserid, tvChipsNumber;
    ImageView ivNavigation;
    DrawerLayout drawer;
    HashMap<String, String> user;
    SessionManager sessionManager;
    String chips_points;
    boolean doubleBackToExitPressedOnce = false;
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;


        sessionManager = new SessionManager(this);

        findView();

        if (sessionManager.getUserDetails() != null) {
            user = sessionManager.getUserDetails();

            chips_points = user.get(SessionManager.CHIPS_POINT);
            String userName = user.get(SessionManager.USERNAME);
            tvUsername.setText(userName);
            // String userId = user.get(SessionManager.USER_ID);
            tvUserid.setText(CommonMethod.getChipsPoints(this));
            tvChipsNumber.setText("Chips: " + CommonMethod.getChipsPoints(this));
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        // bundle.putString("CategoryID", Searchstring.trim());
        fragment.setArguments(bundle);
        gotoFragment(fragment, "HomeFragment");

    }

    private void findView() {
        navView = findViewById(R.id.nav_view);

        View header = navView.getHeaderView(0);
        tvUsername = header.findViewById(R.id.tvUserName);
        tvUserid = header.findViewById(R.id.tvUserid);
        ivNavigation = findViewById(R.id.ivNavigation);
        tvChipsNumber = findViewById(R.id.tvChipsNumber);
        ivNavigation.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        int backpress = 0;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            invalidateOptionsMenu();
        }
        if (!doubleBackToExitPressedOnce) {
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

        //handler.removeCallbacksAndMessages(null);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_settings);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {

            //do whatever you want to do here.
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main_home) {
            gotoFragment(new HomeFragment(), "HomeFragment");
        } else if (id == R.id.nav_main_chips) {
            gotoFragment(new BettingListingFragment(), "BattingCloseFragment");
        } else if (id == R.id.nav_main_batting_history) {
            gotoFragment(new BattingListFragment(), "BattingListFragment");
        } else if (id == R.id.nav_main_logout) {
            new iOSDialogBuilder(MainActivity.this)
                    .setTitle("Logout")
                    .setSubtitle("Are you sure do you want to logout?")
                    .setBoldPositiveLabel(true)
                    .setCancelable(false)
                    .setPositiveListener("Yes", new iOSDialogClickListener() {
                        @Override
                        public void onClick(iOSDialog dialog) {

                            sessionManager.logoutUser();
                            dialog.dismiss();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                finishAffinity();
                            }
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


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void gotoFragment(Fragment fragment, final String constant) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content_framgment, fragment, constant)
                // .addToBackStack(constant)
                .commit();
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
    public void onResume() {
        //start handler as activity become visible

        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                //do something
                if (CommonMethod.isOnline(MainActivity.this)) {
                    chipsPoints();
                    tvUserid.setText(CommonMethod.getChipsPoints(MainActivity.this));
                    tvChipsNumber.setText("Chips: " + CommonMethod.getChipsPoints(MainActivity.this));
                }

                handler.postDelayed(runnable, delay);
            }
        }, delay);

        super.onResume();
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
                                CommonMethod.saveChipsPoints(MainActivity.this, chips_point);

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
                        Toast.makeText(MainActivity.this, "Server Error!!", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", CommonMethod.getUserId(MainActivity.this));
                params.put("type", CommonMethod.getUserType(MainActivity.this));


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }
}
