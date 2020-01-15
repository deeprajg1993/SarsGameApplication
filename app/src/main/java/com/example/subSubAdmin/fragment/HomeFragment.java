package com.example.subSubAdmin.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gameapplication.R;
import com.example.gameapplication.userApp.utlis.BaseURL;
import com.example.gameapplication.userApp.utlis.CommonMethod;
import com.example.gameapplication.userApp.utlis.IsConnected;
import com.example.gameapplication.userApp.utlis.SessionManager;
import com.example.subSubAdmin.model.ChipsPointResponseParser;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

public class HomeFragment extends Fragment implements View.OnClickListener {
    LinearLayout llAddUser, llStatement, llAddDebit, llUserDetails;
    HashMap<String, String> user;
    SessionManager sessionManager;
    String chips_points;
    TextView tvChipsPoint, totalUsers, tvViewAll;
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 2000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        sessionManager = new SessionManager(getActivity());


        tvChipsPoint = view.findViewById(R.id.tvChipsPoint);
        llAddUser = view.findViewById(R.id.llAddUser);
        llStatement = view.findViewById(R.id.llStatement);
        llAddDebit = view.findViewById(R.id.llAddDebit);
        llUserDetails = view.findViewById(R.id.llUserDetails);
        totalUsers = view.findViewById(R.id.totalUsers);
        tvViewAll = view.findViewById(R.id.tvViewAll);

        llAddUser.setOnClickListener(this);
        llStatement.setOnClickListener(this);
        llAddDebit.setOnClickListener(this);
        llUserDetails.setOnClickListener(this);
        tvViewAll.setOnClickListener(this);

        tvChipsPoint.setText("₹ " + CommonMethod.getChipsPoints(getActivity()));
        totalUsers.setText("Total User (" + CommonMethod.getTotalUser(getActivity()) + ")");


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llAddUser:
                gotoFragment(new AddUserFragment(), "Add User");
                break;
            case R.id.llStatement:
                gotoFragment(new StatementFragment(), "Add User");
                break;
            case R.id.llAddDebit:
                gotoFragment(new AddDebitFragment(), "AddDebitFragment");
                break;
            case R.id.llUserDetails:
                gotoFragment(new UserDetailsFragment(), "UserDetailsFragment");
                break;
            case R.id.tvViewAll:
                gotoFragment(new UserDetailsFragment(), "UserDetailsFragment");

                break;
        }
    }

    private void gotoFragment(Fragment fragment, final String constant) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, fragment, constant)
                .addToBackStack(constant)
                .commit();
    }

    @Override
    public void onResume() {
        //start handler as activity become visible

        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                //do something
                if (IsConnected.isInternet_connected(getContext())) {
                    chipsPoints();
                    tvChipsPoint.setText("₹ " + CommonMethod.getChipsPoints(getActivity()));
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
