package com.example.gameapplication.userApp.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.gameapplication.R;
import com.example.gameapplication.userApp.utlis.CommonMethod;
import com.example.gameapplication.userApp.utlis.RuntimePermissionsActivity;
import com.example.gameapplication.userApp.utlis.SessionManager;
import com.example.gameapplication.userApp.utlis.SpumanteRegularShadow;
import com.example.subSubAdmin.activity.SubDashboardActivity;

public class SplashActivity extends RuntimePermissionsActivity {
    private static final int REQUEST_PERMISSIONS = 20;
    private static int SPLASH_TIME_OUT = 1000;
    ImageView ivImage;
    SessionManager sessionManager;
    LinearLayout mainFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        mainFrame = findViewById(R.id.llmainDisplay);

        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this,
                R.anim.hyperspace_jump);
        mainFrame.startAnimation(hyperspaceJumpAnimation);

        sessionManager = new SessionManager(this);

        if (CommonMethod.isOnline(SplashActivity.this)) {
            initialize();
        } else {
            showAlertDialog(SplashActivity.this, "Internet Connection",
                    "You don't have internet connection", false);
        }

        //      initialize();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (isDeviceBuildVersionMarshmallow()) {
            getPermisson();
        } else
            getSession();
    }


    private void getPermisson() {
        SplashActivity.super.requestAppPermissions(new
                        String[]{
                        /* android.Manifest.permission.CAMERA,
                         Manifest.permission.ACCESS_FINE_LOCATION,
                         Manifest.permission.ACCESS_COARSE_LOCATION,
 */
                }, R.string.runtime_permissions_txt
                , REQUEST_PERMISSIONS);
    }

    private boolean isDeviceBuildVersionMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        getSession();
    }

    private void initialize() {
        if (isDeviceBuildVersionMarshmallow()) {
            getPermisson();
        } else {
            getSession();
        }
    }


    /* private void getSession() {
         new Handler().postDelayed(new Runnable() {

             @Override
             public void run() {

                 Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                 startActivity(intent);
                 finish();

             }
         }, SPLASH_TIME_OUT);
     }

    */
    private void getSession() {

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    if (sessionManager.isLoggedIn()) {
                        if (CommonMethod.getUserType(SplashActivity.this).equals("user")) {
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            intent.addFlags(intent.FLAG_ACTIVITY_NO_ANIMATION);

                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(SplashActivity.this, SubDashboardActivity.class);
                            intent.addFlags(intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            finish();
                        }

                    } else {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        intent.addFlags(intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);


                        finish();
                    }
                    /*Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                */
                }
            }
        };
        timer.start();
    }

    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        alertDialog.setTitle(title);
        alertDialog.setMessage(message);

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                finish();
                System.exit(0);
            }
        });
        alertDialog.show();

    }

}