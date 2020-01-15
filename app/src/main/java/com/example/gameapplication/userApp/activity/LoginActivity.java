package com.example.gameapplication.userApp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gameapplication.R;
import com.example.gameapplication.userApp.model.LoginResponse;
import com.example.gameapplication.userApp.utlis.BaseURL;
import com.example.gameapplication.userApp.utlis.CommonMethod;
import com.example.gameapplication.userApp.utlis.IsConnected;
import com.example.gameapplication.userApp.utlis.SessionManager;
import com.example.subSubAdmin.activity.SubDashboardActivity;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvLogin;
    LinearLayout ll_main, llUser;
    EditText etUserId, etPassword;
    SessionManager session;
    RelativeLayout rlMainLayout;
    TextView tvNamefirst, tvName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(this);
        findView();
        setOnClick();

        ll_main = findViewById(R.id.llMain);
        rlMainLayout = findViewById(R.id.rlMainLayout);
        tvNamefirst = findViewById(R.id.tvNamefirst);
        //   llUser = findViewById(R.id.llUser);
        tvName = findViewById(R.id.tvName);
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this,
                R.anim.test_jump);
        ll_main.startAnimation(hyperspaceJumpAnimation);

        Animation JumpAnimation = AnimationUtils.loadAnimation(this,
                R.anim.content_jump);
        Animation Animation = AnimationUtils.loadAnimation(this,
                R.anim.second_jump);

        tvNamefirst.startAnimation(Animation);
//        llUser.startAnimation(Animation);
        tvName.startAnimation(JumpAnimation);
        etUserId.startAnimation(Animation);
        etPassword.startAnimation(JumpAnimation);
        tvLogin.startAnimation(JumpAnimation);
    }

    private void setOnClick() {
        tvLogin.setOnClickListener(this);
    }

    private void findView() {
        tvLogin = findViewById(R.id.tvLogin);
        etPassword = findViewById(R.id.etPassword);
        if (etPassword.getText().length() > 0) {
            etPassword.setSelection(etPassword.getText().length());
        }
        etUserId = findViewById(R.id.etUserId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvLogin:
               /* Intent main = new Intent(LoginActivity.this, SubDashboardActivity.class);
                startActivity(main);
                finish();*/
                if (validateAllDetails()) {

                    if (IsConnected.isInternet_connected(this)) {
                        userLogin();
                    } else {
                        Toast.makeText(this, "Internet not connected !", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private void userLogin() {
        final ProgressDialog progress = ProgressDialog.show(LoginActivity.this, "", "Please wait...", true);
        progress.setCancelable(false);

        final String url = BaseURL.LOGIN_URL;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        System.out.println("Response===" + response);

                        try {
                            LoginResponse mSignUpResponseJson = new Gson().fromJson(response, LoginResponse.class);

                            if (mSignUpResponseJson.getStatus().equals("1")) {
                                if (mSignUpResponseJson.getUser_type().equalsIgnoreCase("user")) {

                                    String id = mSignUpResponseJson.getId();
                                    String user_id = mSignUpResponseJson.getUser_id();
                                    String chip_point = mSignUpResponseJson.getChip_point();
                                    String type = mSignUpResponseJson.getUser_type();
                                    String total_user = mSignUpResponseJson.getTotal_user();

                                    CommonMethod.saveTotalUser(LoginActivity.this, total_user);

                                    session.createLoginSession(mSignUpResponseJson);//Dashboard
                                    CommonMethod.saveUserId(LoginActivity.this, id);
                                    CommonMethod.saveUserType(LoginActivity.this, type);
                                    CommonMethod.saveChipsPoints(LoginActivity.this, chip_point);
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    //intent.putExtra("userName", userName);
                                    startActivity(intent);
                                    finish();

                                    Toast.makeText(LoginActivity.this, mSignUpResponseJson.getMessage(), Toast.LENGTH_SHORT).show();

                                } else {

                                    String id = mSignUpResponseJson.getId();
                                    String total_user = mSignUpResponseJson.getUser_id();
                                    String chip_point = mSignUpResponseJson.getChip_point();
                                    String type = mSignUpResponseJson.getUser_type();
                                    CommonMethod.saveChipsPoints(LoginActivity.this, chip_point);
                                    session.createLoginSession(mSignUpResponseJson);//Dashboard
                                    CommonMethod.saveUserId(LoginActivity.this, id);
                                    CommonMethod.saveUserType(LoginActivity.this, type);
                                    CommonMethod.saveTotalUser(LoginActivity.this, total_user);
                                    String totalValue = mSignUpResponseJson.getChip_point();
                                    CommonMethod.saveChipsPoints(LoginActivity.this, totalValue);
                                    Intent intent = new Intent(LoginActivity.this, SubDashboardActivity.class);
                                    //intent.putExtra("userName", userName);
                                    startActivity(intent);
                                    finish();

                                    Toast.makeText(LoginActivity.this, mSignUpResponseJson.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "" + mSignUpResponseJson.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.dismiss();
                        System.out.println("Error" + error.toString());
                        Toast.makeText(LoginActivity.this, "Server Error!!", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("req", "index");

                params.put("user_id", etUserId.getText().toString().trim());
                params.put("password", etPassword.getText().toString().trim());


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    private boolean validateAllDetails() {
        boolean checkFields = true;
        if (!emptyPassword()) {
            checkFields = false;
        }
        if (!validateUserName()) {
            checkFields = false;
        }

        return checkFields;
    }

    private boolean emptyPassword() {
        if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError("Please Enter Password");
            requestFocus(etPassword);
            return false;
        } else {
            //  inputLayoutPassword.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateUserName() {
        if (etUserId.getText().toString().isEmpty()) {
            etUserId.setError("Please enter User id.");
            requestFocus(etUserId);
            return false;
        }
        return true;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}
