package com.example.subSubAdmin.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gameapplication.R;
import com.example.gameapplication.userApp.utlis.BaseURL;
import com.example.gameapplication.userApp.utlis.CommonMethod;
import com.example.subSubAdmin.adpater.AddDebitAdpater;
import com.example.subSubAdmin.adpater.StatementAdpater;
import com.example.subSubAdmin.adpater.UserDetailsAdpater;
import com.example.subSubAdmin.model.AddDebitResponse;
import com.example.subSubAdmin.model.ChipsPointResponseParser;
import com.example.subSubAdmin.model.StatementResponse;
import com.example.subSubAdmin.model.UserDetailsResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddDebitFragment extends Fragment {
    RecyclerView rcAddDebit;
    AddDebitAdpater mAdpater;
    List<AddDebitResponse> mList;
    TextView tvTotalUser;
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 1 * 60 * 1000;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_debit_fragment, container, false);

        rcAddDebit = view.findViewById(R.id.rcAddDebit);
        tvTotalUser = view.findViewById(R.id.tvTotalUser);

        if (CommonMethod.isOnline(getContext())) {
            userDetailsListApiCall();
        } else {
            Toast.makeText(getContext(), "Please Connect to Internet", Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    public void userDetailsListApiCall() {
        final ProgressDialog progress = ProgressDialog.show(getContext(), "", "Please wait...", true);
        progress.setCancelable(false);

        final String url = BaseURL.ADDUSERLIST;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        System.out.println("Response===" + response);

                        try {
                            AddDebitResponse mAddDebitList = new Gson().fromJson(response, AddDebitResponse.class);

                            if (mAddDebitList.getStatus().equals("1")) {
                                mAdpater = new AddDebitAdpater(getContext(), mAddDebitList.getData());
                                rcAddDebit.setLayoutManager(new LinearLayoutManager(getContext()));
                                rcAddDebit.setAdapter(mAdpater);
                                String totalUser = String.valueOf(mAddDebitList.getData().size());
                                tvTotalUser.setText("Total User (" + totalUser + ")");
                                CommonMethod.saveTotalUser(getActivity(), totalUser);
                                mAdpater.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getContext(), "" + mAddDebitList.getMessage(), Toast.LENGTH_LONG).show();
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
                params.put("dl_id", CommonMethod.getUserId(getContext()));


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


    @Override
    public void onResume() {
        //start handler as activity become visible

        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                //do something
                if (CommonMethod.isOnline(getActivity())) {
                    chipsPoints();
                } else {
                    Toast.makeText(getActivity(), "Please Connect to internet", Toast.LENGTH_SHORT).show();
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

                                Toast.makeText(getContext(), "" + mDashboard.getMessage(), Toast.LENGTH_SHORT).show();
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
