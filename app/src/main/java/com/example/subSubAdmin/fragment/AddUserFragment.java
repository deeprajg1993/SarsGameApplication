package com.example.subSubAdmin.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gameapplication.R;
import com.example.gameapplication.userApp.activity.LoginActivity;
import com.example.gameapplication.userApp.activity.MainActivity;
import com.example.gameapplication.userApp.model.LoginResponse;
import com.example.gameapplication.userApp.utlis.BaseURL;
import com.example.gameapplication.userApp.utlis.CommonMethod;
import com.example.subSubAdmin.activity.SubDashboardActivity;
import com.example.subSubAdmin.model.ChipsPointResponseParser;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;


public class AddUserFragment extends Fragment implements View.OnClickListener {

    EditText et_UserName, etUserPassword, etAddChips;
    TextView tvCreateUser;
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 5000;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_user_fragment, container, false);

        findView(view);
        setOnClick();

        return view;
    }

    private void setOnClick() {
        tvCreateUser.setOnClickListener(this);
    }

    private void findView(View view) {
        et_UserName = view.findViewById(R.id.et_UserName);
        etUserPassword = view.findViewById(R.id.etUserPassword);
        etAddChips = view.findViewById(R.id.etAddChips);
        tvCreateUser = view.findViewById(R.id.tvCreateUser);
    }


    private boolean validateAllDetails() {
        boolean checkFields = true;
        if (!validateUserChips()) {
            checkFields = false;
        }
        if (!emptyPassword()) {
            checkFields = false;
        }
        if (!validateUserName()) {
            checkFields = false;
        }

        return checkFields;
    }

    private boolean validateUserChips() {
        if (etAddChips.getText().toString().isEmpty()) {
            etAddChips.setError("Please enter Chips Number.");
            requestFocus(etAddChips);
            return false;
        }
        return true;
    }

    private boolean emptyPassword() {
        if (etUserPassword.getText().toString().isEmpty()) {
            etUserPassword.setError("Please Enter Password");
            requestFocus(etUserPassword);
            return false;
        } else {
            //  inputLayoutPassword.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateUserName() {
        if (et_UserName.getText().toString().isEmpty()) {
            et_UserName.setError("Please enter User Name.");
            requestFocus(et_UserName);
            return false;
        }
        return true;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCreateUser:
                if (validateAllDetails()) {
                    if (CommonMethod.isOnline(getActivity())) {
                        int addvalue = Integer.parseInt(etAddChips.getText().toString());
                        int totalAmount = Integer.parseInt(CommonMethod.getChipsPoints(getActivity()));
                        if (addvalue < totalAmount) {
                            addUser();
                        } else {
                            Toast.makeText(getActivity(), "you have not insufficient Chips", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Internet not connected !", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private void addUser() {
        final ProgressDialog progress = ProgressDialog.show(getContext(), "", "Please wait...", true);
        progress.setCancelable(false);

        final String url = BaseURL.ADDUSER;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        System.out.println("Response===" + response);

                        try {
                            LoginResponse mSignUpResponseJson = new Gson().fromJson(response, LoginResponse.class);

                            if (mSignUpResponseJson.getStatus().equals("1")) {
                                Intent intent = new Intent(getContext(), SubDashboardActivity.class);
                                //intent.putExtra("userName", userName);
                                int totalValue = Integer.parseInt(CommonMethod.getTotalUser(getContext()));
                                String total = String.valueOf(totalValue + 1);
                                CommonMethod.saveTotalUser(getContext(), total);
                                startActivity(intent);
                                Toast.makeText(getContext(), mSignUpResponseJson.getMessage(), Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getContext(), "" + mSignUpResponseJson.getMessage(), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getContext(), "Server Error!!", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                // params.put("req", "AddUser");

                params.put("user_id", "user@" + et_UserName.getText().toString().trim());
                params.put("password", etUserPassword.getText().toString().trim());
                params.put("chips", etAddChips.getText().toString().trim());
                params.put("dl_chips", CommonMethod.getChipsPoints(getContext()));
                params.put("dl_id", CommonMethod.getUserId(getContext()));

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    @Override
    public void onResume() {
        //start handler as activity become visible

        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                //do something
                if (CommonMethod.isOnline(getContext())) {
                    chipsPoints();
                } else {
                    Toast.makeText(getContext(), "Please Connect to internet", Toast.LENGTH_SHORT).show();
                }

                handler.postDelayed(runnable, delay);
            }
        }, delay);

        super.onResume();
    }

// If onPause() is not included the threads will double up when you
// reload the activity

    /*@Override
    public void onPause() {
        handler.removeCallbacks(runnable); //stop handler when activity not visible
        super.onPause();
    }
*/

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
                                CommonMethod.saveChipsPoints(getContext(), chips_point);

                                //      Toast.makeText(getContext(), "" + mDashboard.getMessage(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getContext(), "Server Error!!", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", CommonMethod.getUserId(getContext()));
                params.put("type", CommonMethod.getUserType(getContext()));


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }


}